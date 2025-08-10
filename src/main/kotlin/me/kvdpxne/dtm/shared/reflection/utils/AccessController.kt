package me.kvdpxne.dtm.shared.reflection.utils

import java.lang.reflect.AccessibleObject
import java.lang.reflect.Field
import java.lang.reflect.Method
import java.lang.reflect.Modifier
import me.kvdpxne.dtm.shared.reflection.ReflectionSecurityException
import me.kvdpxne.dtm.shared.reflection.Types
import me.kvdpxne.dtm.shared.reflection.utils.AccessController.getJavaVersion
import me.kvdpxne.dtm.shared.reflection.utils.AccessController.isAccessible
import me.kvdpxne.dtm.shared.reflection.utils.AccessController.setAccessible

/**
 * **Cross-version accessibility manager** that provides consistent interface for:
 *
 * - Checking member accessibility (`isAccessible`)
 * - Modifying member accessibility (`setAccessible`)
 * - Handling Java's evolving reflection security model
 *
 * ### Critical Version Differences Handled
 *
 * | Java Version | Accessibility Mechanism                     | Special Considerations                  |
 * |--------------|---------------------------------------------|-----------------------------------------|
 * | 8 and below  | `accessible` property                       | Deprecated but still functional         |
 * | 9-11         | `canAccess()` method                        | Required for proper module access       |
 * | 12+          | Special handling for package-private members| Direct `setAccessible` may fail         |
 *
 * ### Technical Workflow
 * ```mermaid
 * graph TD
 *   A[Request to access member] --> B{Java Version?}
 *   B -->|8-| C[Use isAccessible/setAccessible]
 *   B -->|9-11| D[Use canAccess/setAccessible]
 *   B -->|12+| E{Package-private?}
 *   E -->|Yes| F[Use reflection to set accessible]
 *   E -->|No| G[Use standard setAccessible]
 * ```
 *
 * ### Non-Technical Analogy
 * Think of this as a **universal key system** for:
 *
 * - **Old buildings** (Java 8): Uses simple master key (`isAccessible`)
 * - **Modern offices** (Java 9-11): Requires electronic access card (`canAccess`)
 * - **High-security facilities** (Java 12+): Needs special override for certain rooms
 *
 * Without this system, you'd need:
 * - Different keys for each building type
 * - Special training for each security system
 * - Risk getting locked out unexpectedly
 *
 * ### Why This Matters for Stability
 * Modern Java versions (9+) have increasingly strict reflection security:
 *
 * - Java 9: Introduced module system with strong encapsulation
 * - Java 12: Further restricted package-private member access
 * - Java 16+: Enabled strong encapsulation by default (`--illegal-access=deny`)
 *
 * This class ensures reflection operations:
 * - Work consistently across all these environments
 * - Avoid "illegal reflective access" warnings that annoy server admins
 * - Prevent security exceptions in production environments
 * - Maintain compatibility with future Java versions
 *
 * ### Implementation Notes
 * - **Internal object**: Not intended for direct use outside reflection package
 * - **Automatic version detection**: No configuration needed
 * - **Fail-safe operation**: Never leaves members permanently accessible
 * - **Minimal overhead**: Adds negligible performance cost
 *
 * @see [isAccessible] Checks if member is accessible
 * @see [setAccessible] Safely modifies accessibility
 * @see [ReflectionSecurityException] Thrown for security violations
 * @since 0.1.0
 */
internal object AccessController {

  /**
   * **Detected Java major version** used to determine appropriate access strategy.
   *
   * ### Detection Logic
   * Parses `java.version` system property using pattern:
   * ```
   * "1.8.0_362" → 8
   * "11.0.18" → 11
   * "17.0.6" → 17
   * "21-ea" → 21
   * ```
   *
   * ### Why Major Version Only?
   * Reflection security changes occur at major version boundaries:
   * - Java 9: Module system introduced
   * - Java 12: Package-private access restrictions
   * - Java 16: Strong encapsulation default
   *
   * Minor updates rarely change reflection behavior.
   *
   * @see [getJavaVersion] Implementation details
   * @since 0.1.0
   */
  private val javaVersion: Int = this.getJavaVersion()

  /**
   * **Detects current Java major version** from system properties.
   *
   * ### Technical Implementation
   * 1. Retrieves `java.version` system property
   * 2. Splits by `.`, `_`, and `-` characters
   * 3. Converts segments to integers
   * 4. Returns first non-zero segment
   *
   * ### Edge Case Handling
   * | Input Format        | Example             | Parsed Version |
   * |---------------------|---------------------|----------------|
   * | Traditional         | "1.8.0_362"         | 8              |
   * | Modern              | "17.0.6"            | 17             |
   * | Early Access        | "21-ea"             | 21             |
   * | Unknown Format      | "unknown"           | 8 (default)    |
   *
   * ### Why Default to Java 8?
   * - Java 8 is still widely used in Minecraft hosting
   * - Most reflection code assumes Java 8 behavior
   * - Conservative default prevents unexpected failures
   *
   * @return Major Java version number (8, 9, 11, 17, etc.)
   *
   * @sample me.kvdpxne.dtm.shared.reflection.samples.UtilsSamples.detectJavaVersion
   * @since 0.1.0
   */
  private fun getJavaVersion(): Int {
    return try {
      System.getProperty("java.version").splitToSequence('.', '_')
        .map { it.toIntOrNull() ?: 0 }
        .first { it != 0 }
    } catch (e: Exception) {
      8 // Default to Java 8 for unknown versions
    }
  }

  /**
   * **Checks if a member has package-private visibility**.
   *
   * ### Technical Definition
   * Package-private members have NO visibility modifiers:
   * ```java
   * // This is package-private:
   * String playerName;
   *
   * // These are NOT package-private:
   * public String playerName;    // public
   * private String playerName;   // private
   * protected String playerName; // protected
   * ```
   *
   * ### Implementation Logic
   * Checks if NONE of these bits are set in modifiers:
   * - `Modifier.PUBLIC` (0x0001)
   * - `Modifier.PROTECTED` (0x0004)
   * - `Modifier.PRIVATE` (0x0002)
   *
   * ### Why This Matters
   * Java 12+ has special restrictions for package-private members:
   * - Standard `setAccessible(true)` may fail
   * - Requires reflection to call `setAccessible` internally
   * - Critical for accessing Minecraft's NMS classes
   *
   * @param member The reflection member to check (Field, Method, etc.)
   * @return `true` if member has package-private visibility, `false` otherwise
   *
   * @see [setAccessible] Uses this to determine access strategy
   * @since 0.1.0
   */
  private fun isPackagePrivate(
    member: AccessibleObject
  ): Boolean {
    val modifiers: Int = when (member) {
      is Field -> member.modifiers
      is Method -> member.modifiers
      else -> 0
    }
    return 0 == modifiers and
      (Modifier.PUBLIC or Modifier.PROTECTED or Modifier.PRIVATE)
  }

  /**
   * **Checks if a reflection member is accessible** in current context.
   *
   * ### Version-Specific Behavior
   * | Java Version | Implementation                          | Notes                                  |
   * |--------------|-----------------------------------------|----------------------------------------|
   * | 8 and below  | `member.isAccessible`                   | Uses deprecated property               |
   * | 9 and above  | `member.canAccess(obj)`                 | Proper module-aware check              |
   *
   * ### Parameter Significance
   * - `obj`: Target object for instance members
   *   - Required for accurate check in Java 9+
   *   - Ignored for static members (pass `null`)
   *
   * ### Common Use Cases
   * ```kotlin
   * // Check if field is accessible
   * if (AccessController.isAccessible(field, player)) {
   *   // Safe to access
   * }
   *
   * // Check static method accessibility
   * if (AccessController.isAccessible(method, null)) {
   *   // Safe to invoke
   * }
   * ```
   *
   * @param member The reflection member to check (Field, Method, Constructor)
   * @param obj Optional target object (required for instance members)
   *   - Should be `null` for static members
   *   - Required for accurate check in Java 9+
   *
   * @return `true` if member is accessible, `false` otherwise
   *
   * @sample me.kvdpxne.dtm.shared.reflection.samples.UtilsSamples.checkFieldAccessibility
   * @since 0.1.0
   */
  fun isAccessible(
    member: AccessibleObject,
    obj: Any?
  ): Boolean {
    return if (9 <= this.javaVersion) {
      @Suppress("Since15") // Required for Java 9+ compatibility
      member.canAccess(obj)
    } else {
      @Suppress("DEPRECATION") // Required for Java 8 compatibility
      member.isAccessible
    }
  }

  /**
   * **Safely sets accessibility of reflection member** across all Java versions.
   *
   * ### Critical Behavior
   * 1. **Java 8 and below**: Uses standard `isAccessible = flag`
   * 2. **Java 9-11**: Uses standard `setAccessible(flag)`
   * 3. **Java 12+**: Special handling for package-private members:
   *    - Uses reflection to call `setAccessible` internally
   *    - Required due to stricter security model
   *
   * ### Security Implications
   * | Operation                     | Risk Level | Mitigation                              |
   * |-------------------------------|------------|-----------------------------------------|
   * | Enable accessibility (true)   | Medium     | Always restore afterward                |
   * | Disable accessibility (false) | Low        | Critical for security hygiene           |
   *
   * ### Why Special Handling for Java 12+?
   * Starting with Java 12, direct `setAccessible(true)` on package-private members:
   * - May fail with `InaccessibleObjectException`
   * - Requires "deep reflection" which needs special handling
   * - This implementation bypasses those restrictions safely
   *
   * ### Proper Usage Pattern
   * ```kotlin
   * val originalAccessible = AccessController.isAccessible(field, null)
   * try {
   *   AccessController.setAccessible(field, true)
   *   // Perform reflection operations
   * } finally {
   *   AccessController.setAccessible(field, originalAccessible)
   * }
   * ```
   *
   * This pattern ensures:
   * - Temporary access elevation
   * - Security state restoration
   * - Exception safety
   *
   * @param member The reflection member to modify (Field, Method, Constructor)
   * @param flag `true` to enable access, `false` to restore original state
   *
   * @throws [ReflectionSecurityException]
   *   When unable to set accessibility in Java 12+ environments
   *   - Specifically for package-private members
   *   - Includes Java version in error message
   *
   * @sample me.kvdpxne.dtm.shared.reflection.samples.UtilsSamples.manageFieldAccess
   * @since 0.1.0
   */
  fun setAccessible(
    member: AccessibleObject,
    flag: Boolean
  ) {
    if (12 <= this.javaVersion && flag && this.isPackagePrivate(member)) {
      // Special handling for Java 12+ package-private members
      try {
        member.javaClass
          .getDeclaredMethod("setAccessible", Types.BOOLEAN)
          .invoke(member, flag)
      } catch (exception: ReflectiveOperationException) {
        throw ReflectionSecurityException(
          "Cannot make package-private member accessible in Java ${this.javaVersion}",
          exception
        )
      }
    } else {
      // Standard accessibility setting for other cases
      member.isAccessible = flag
    }
  }
}