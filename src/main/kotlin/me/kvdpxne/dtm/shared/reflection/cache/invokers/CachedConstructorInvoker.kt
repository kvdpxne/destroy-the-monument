package me.kvdpxne.dtm.shared.reflection.cache.invokers

import java.lang.reflect.Constructor
import java.lang.reflect.InvocationTargetException
import me.kvdpxne.dtm.shared.reflection.ReflectionException
import me.kvdpxne.dtm.shared.reflection.ReflectionSecurityException
import me.kvdpxne.dtm.shared.reflection.accessors.ConstructorInvoker
import me.kvdpxne.dtm.shared.reflection.utils.AccessController

/**
 * **Thread-safe implementation** of [ConstructorInvoker] that:
 *
 * - Caches constructor accessibility state
 * - Properly handles `final` class instantiation
 * - Translates JVM exceptions to domain-specific errors
 * - Supports Java 8-17+ reflection security models
 *
 * ### Critical Behavior
 * 1. **Temporarily enables access** to the constructor during invocation
 * 2. **Restores original accessibility** after operation (prevents security leaks)
 * 3. **Unboxes InvocationTargetException** to show actual error cause
 * 4. **Validates parameter counts** against constructor signature
 *
 * ### Non-Technical Analogy
 * Like a specialized 3D printer operator who:
 * - Knows how to bypass printer security (accessibility)
 * - Only disables security *while printing* (temporary access)
 * - Understands blueprint errors (parameter mismatches)
 * - Works with different printer models (Java versions)
 *
 * ### Why Not Use Directly?
 * This class is **internal implementation detail** - always use via:
 * ```kotlin
 * Reflection.getConstructor(playerClass).invoke(parameters)
 * ```
 * Direct usage would:
 * - Bypass cache benefits
 * - Risk accessibility leaks
 * - Create version compatibility issues
 *
 * @property constructor The underlying Java [Constructor] being wrapped
 * @property originalAccessible Original accessibility state before modification
 * @property className Fully qualified class name for error reporting
 *
 * @see [ConstructorInvoker] Base interface being implemented
 * @see [Reflection.getConstructor] Recommended access point
 * @see [AccessController] Handles cross-version accessibility
 * @since 0.1.0
 */
internal class CachedConstructorInvoker internal constructor(
  private val constructor: Constructor<*>,
  private val originalAccessible: Boolean,
  private val className: String
) : ConstructorInvoker {

  init {
    // Immediately enable access to constructor
    // This is safe because we'll restore state in finally block during invocation
    AccessController.setAccessible(this.constructor, true)
  }

  /**
   * Creates a new instance through the target constructor with proper:
   *
   * 1. **Accessibility management**:
   *    - Enables access before invocation
   *    - Restores original state after (even on failure)
   * 2. **Exception translation**:
   *    - `InvocationTargetException` → Unwrapped cause
   *    - `InstantiationException` → Clear class error
   *    - `IllegalAccessException` → Security context
   * 3. **Parameter handling**:
   *    - Converts `null` to empty array for no-arg constructors
   *    - Supports primitive/wrapper type matching
   *
   * ### Parameter Rules
   * | Scenario                          | Required Format               |
   * |-----------------------------------|-------------------------------|
   * | No parameters                     | `null` or empty array         |
   * | Primitive parameters (int, float) | Boxed values (Int, Float)     |
   * | Nullable parameters               | `null` allowed                |
   *
   * ### Error Mapping
   * | Java Exception                    | Translated To                          | Common Cause                                     |
   * |-----------------------------------|----------------------------------------|--------------------------------------------------|
   * | `InvocationTargetException`       | [ReflectionException] with real cause  | Constructor threw exception                      |
   * | `InstantiationException`          | [ReflectionException]                  | Abstract class or interface instantiation        |
   * | `IllegalAccessException`          | [ReflectionSecurityException]          | Security manager blocked access                  |
   *
   * @param parameters Optional array of constructor arguments (default = `null`)
   *   - Must match constructor's parameter count and types
   *   - `null` treated as empty array for no-arg constructors
   *   - Primitive types must be boxed (e.g., `Int` not `int`)
   *
   * @return New instance of the target class
   *   - Never `null` (construction failures throw exceptions)
   *   - Type matches the class used to create this invoker
   *
   * @throws [ReflectionException]
   *   When constructor execution fails due to:
   *   - Invalid parameters (wrong count/types)
   *   - Exceptions thrown *by* the constructor
   *   - Attempting to instantiate abstract class
   *
   * @throws [ReflectionSecurityException]
   *   When Java's security system blocks constructor access
   *   - Common in modular Java (9+) with strict security policies
   *
   * @sample me.kvdpxne.dtm.shared.reflection.samples.ConstructorSamples.createEntityInstance
   * @since 0.1.0
   */
  override fun invoke(
    parameters: Array<Any?>?
  ): Any {
    return try {
      this.constructor.newInstance(parameters ?: emptyArray<Any>())
    } catch (exception: InvocationTargetException) {
      throw ReflectionException(
        "Error constructing ${this.className}",
        exception.cause ?: exception
      )
    } catch (exception: InstantiationException) {
      throw ReflectionException(
        "Cannot instantiate abstract class ${this.className}",
        exception
      )
    } catch (exception: IllegalAccessException) {
      throw ReflectionSecurityException(
        "Illegal access to constructor of ${this.className}",
        exception
      )
    } finally {
      // CRITICAL: Always restore original accessibility state
      // Prevents security leaks and "illegal reflective access" warnings
      if (!this.originalAccessible) {
        AccessController.setAccessible(this.constructor, false)
      }
    }
  }
}