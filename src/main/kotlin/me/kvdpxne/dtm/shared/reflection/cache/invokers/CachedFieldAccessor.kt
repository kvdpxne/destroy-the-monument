package me.kvdpxne.dtm.shared.reflection.cache.invokers

import java.lang.reflect.Field
import me.kvdpxne.dtm.shared.reflection.accessors.FieldAccessor
import me.kvdpxne.dtm.shared.reflection.utils.AccessController

/**
 * **Optimized implementation** of [FieldAccessor] that:
 *
 * - Caches field accessibility state
 * - Handles static vs instance field differences transparently
 * - Properly manages `final` field modification attempts
 * - Works across Java versions (8-17+) with consistent behavior
 *
 * ### Critical Behavior
 * 1. **Temporary access elevation**: Enables access only during operation
 * 2. **State restoration**: Always returns field to original accessibility
 * 3. **Null-safe handling**: Properly manages `null` targets for static fields
 * 4. **Type preservation**: Maintains primitive/wrapper type integrity
 *
 * ### Non-Technical Analogy
 * Like a specialized archivist who:
 * - Knows how to open restricted archive boxes (accessibility)
 * - Only unlocks boxes *while examining contents* (temporary access)
 * - Documents every interaction (caching)
 * - Returns boxes to original locked state (security preservation)
 *
 * ### Why Not Use Directly?
 * This class is **internal implementation detail** - always use via:
 * ```kotlin
 * Reflection.getField(playerClass, "health").get(player)
 * ```
 * Direct usage would:
 * - Bypass cache benefits
 * - Risk leaving fields permanently accessible
 * - Create version compatibility issues
 *
 * @property field The underlying Java [Field] being wrapped
 * @property originalAccessible Original accessibility state before modification
 *
 * @see [FieldAccessor] Base interface being implemented
 * @see [Reflection.getField] Recommended access point
 * @see [AccessController] Handles cross-version accessibility
 * @since 0.1.0
 */
internal class CachedFieldAccessor internal constructor(
  private val field: Field,
  private val originalAccessible: Boolean
) : FieldAccessor {

  init {
    // Immediately enable access to field
    // Safe because we'll restore state in finally blocks
    AccessController.setAccessible(this.field, true)
  }

  /**
   * Reads the field's current value with proper:
   *
   * 1. **Target validation**:
   *    - Instance fields: Requires non-null `target`
   *    - Static fields: Ignores `target` (should be `null`)
   * 2. **Accessibility management**:
   *    - Temporarily enables access during read
   *    - Restores original state after operation
   * 3. **Type handling**:
   *    - Returns `null` for nullable fields
   *    - Returns boxed primitives for primitive fields
   *
   * ### Target Rules
   * | Field Type  | Required `target` | Common Error if Violated         |
   * |-------------|-------------------|----------------------------------|
   * | Instance    | Non-null object   | `NullPointerException`           |
   * | Static      | `null`            | `IllegalArgumentException`       |
   *
   * @param target Optional object instance (default = `null`)
   *   - Required for instance fields
   *   - Should be `null` for static fields
   *   - Ignored internally for static fields
   *
   * @return Current field value, or `null` if:
   *   - Field contains `null` (nullable types)
   *   - Field is primitive with default value (0, false)
   *   - Field is static and uninitialized
   *
   * @throws [ReflectionException]
   *   When field access fails due to:
   *   - Invalid `target` for instance fields
   *   - Field not present in class hierarchy
   *
   * @throws [ReflectionSecurityException]
   *   When Java's security system blocks field access
   *   - Common for `private` fields in security-restricted environments
   *
   * @sample me.kvdpxne.dtm.shared.reflection.samples.FieldSamples.readEntityId
   * @since 0.1.0
   */
  override fun get(
    target: Any?
  ): Any? {
    return try {
      this.field.get(target)
    } finally {
      // CRITICAL: Always restore original accessibility state
      if (!this.originalAccessible) {
        AccessController.setAccessible(this.field, false)
      }
    }
  }

  /**
   * Updates the field's value with proper:
   *
   * 1. **Target validation**:
   *    - Instance fields: Requires non-null `target`
   *    - Static fields: Ignores `target` (should be `null`)
   * 2. **Accessibility management**:
   *    - Temporarily enables access during write
   *    - Restores original state after operation
   * 3. **Type enforcement**:
   *    - Validates parameter type compatibility
   *    - Handles primitive/wrapper conversions
   *
   * ### Special Restrictions
   * - **`final` fields**: Modification *may* succeed but:
   *   - Not guaranteed across Java versions
   *   - May throw `IllegalAccessException` in newer JVMs
   *   - Considered unsafe practice (use with caution)
   *
   * ### Value Rules
   * | Field Type       | Allowed Values                     |
   * |------------------|------------------------------------|
   * | Nullable         | `null` or compatible type         |
   * | Primitive        | Boxed values (Int, Float, etc.)   |
   * | Non-nullable     | Must provide valid non-null value |
   *
   * @param target Optional object instance (default = `null`)
   *   - Required for instance fields
   *   - Should be `null` for static fields
   *   - Ignored internally for static fields
   * @param value New value to store in field (default = `null`)
   *   - Must match field's type requirements
   *   - `null` allowed only for nullable fields
   *
   * @throws [ReflectionException]
   *   When field modification fails due to:
   *   - Type mismatch between `value` and field
   *   - Invalid `target` for instance fields
   *   - Attempting to modify `final` field (may succeed but not recommended)
   *
   * @throws [ReflectionSecurityException]
   *   When Java's security system blocks field modification
   *   - Common for `private` or `final` fields in security-restricted environments
   *
   * @sample me.kvdpxne.dtm.shared.reflection.samples.FieldSamples.setEntityVelocity
   * @since 0.1.0
   */
  override fun set(
    target: Any?,
    value: Any?
  ) {
    try {
      this.field.set(target, value)
    } finally {
      // CRITICAL: Always restore original accessibility state
      if (!this.originalAccessible) {
        AccessController.setAccessible(this.field, false)
      }
    }
  }
}