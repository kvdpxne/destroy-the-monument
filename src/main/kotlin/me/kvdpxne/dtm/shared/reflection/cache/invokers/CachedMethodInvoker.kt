package me.kvdpxne.dtm.shared.reflection.cache.invokers

import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method
import me.kvdpxne.dtm.shared.reflection.ReflectionException
import me.kvdpxne.dtm.shared.reflection.ReflectionSecurityException
import me.kvdpxne.dtm.shared.reflection.accessors.MethodInvoker
import me.kvdpxne.dtm.shared.reflection.utils.AccessController

/**
 * **High-performance implementation** of [MethodInvoker] that:
 *
 * - Caches method accessibility state
 * - Handles static vs instance method differences transparently
 * - Unboxes `InvocationTargetException` to show real errors
 * - Supports Java 8-17+ reflection security models
 *
 * ### Critical Behavior
 * 1. **Temporary access elevation**: Enables access only during invocation
 * 2. **State restoration**: Always returns method to original accessibility
 * 3. **Exception translation**: Converts JVM exceptions to domain errors
 * 4. **Parameter validation**: Ensures correct count and types
 *
 * ### Non-Technical Analogy
 * Like a specialized machine operator who:
 * - Knows how to bypass machine security locks (accessibility)
 * - Only disables locks *while operating* (temporary access)
 * - Understands error codes (exception translation)
 *   - Distinguishes machine faults (method errors) from access issues
 * - Works with different machine models (Java versions)
 *
 * ### Why Not Use Directly?
 * This class is **internal implementation detail** - always use via:
 * ```kotlin
 * Reflection.getMethod(playerClass, "sendMessage").invoke(player, arrayOf("Hello"))
 * ```
 * Direct usage would:
 * - Bypass cache benefits
 * - Risk leaving methods permanently accessible
 * - Create version compatibility issues
 *
 * @property method The underlying Java [Method] being wrapped
 * @property originalAccessible Original accessibility state before modification
 * @property className Fully qualified class name for error reporting
 * @property methodName Method name for precise error context
 *
 * @see [MethodInvoker] Base interface being implemented
 * @see [Reflection.getMethod] Recommended access point
 * @see [AccessController] Handles cross-version accessibility
 * @since 0.1.0
 */
internal class CachedMethodInvoker(
  private val method: Method,
  private val originalAccessible: Boolean,
  private val className: String,
  private val methodName: String
) : MethodInvoker {

  init {
    // Immediately enable access to method
    // Safe because we'll restore state in finally block during invocation
    AccessController.setAccessible(this.method, true)
  }

  /**
   * Executes the target method with proper:
   *
   * 1. **Target validation**:
   *    - Instance methods: Requires non-null `target`
   *    - Static methods: Requires `null` target
   * 2. **Accessibility management**:
   *    - Temporarily enables access during invocation
   *    - Restores original state after operation
   * 3. **Exception translation**:
   *    - `InvocationTargetException` → Unwrapped cause
   *    - `IllegalAccessException` → Security context
   * 4. **Parameter handling**:
   *    - Converts `null` to empty array for no-arg methods
   *    - Supports primitive/wrapper type matching
   *
   * ### Target Rules
   * | Method Type | Required `target` | Common Error if Violated         |
   * |-------------|-------------------|----------------------------------|
   * | Instance    | Non-null object   | `NullPointerException`           |
   * | Static      | `null`            | `IllegalArgumentException`       |
   *
   * ### Error Mapping
   * | Java Exception                    | Translated To                          | Common Cause                                     |
   * |-----------------------------------|----------------------------------------|--------------------------------------------------|
   * | `InvocationTargetException`       | [ReflectionException] with real cause  | Method threw exception during execution          |
   * | `IllegalAccessException`          | [ReflectionSecurityException]          | Security manager blocked access                  |
   *
   * @param target Optional object instance (default = `null`)
   *   - Required for instance methods
   *   - Required to be `null` for static methods
   * @param parameters Optional array of method arguments (default = `null`)
   *   - Must match method's parameter count and types
   *   - `null` treated as empty array for no-arg methods
   *   - Primitive types must be boxed (e.g., `Int` not `int`)
   *
   * @return Method return value, or `null` if:
   *   - Method has `void` return type
   *   - Method returns `null` explicitly
   *   - Return type is nullable and value is absent
   *
   * @throws [ReflectionException]
   *   When method execution fails due to:
   *   - Invalid parameters (wrong count/types)
   *   - Exceptions thrown *by* the method
   *   - Signature mismatch (checked during acquisition)
   *
   * @throws [ReflectionSecurityException]
   *   When Java's security system blocks method access
   *   - Common in modular Java (9+) with strict security policies
   *
   * @sample me.kvdpxne.dtm.shared.reflection.samples.MethodSamples.sendPlayerMessage
   * @since 0.1.0
   */
  override fun invoke(
    target: Any?,
    parameters: Array<Any?>?
  ): Any? {
    return try {
      this.method.invoke(target, parameters ?: emptyArray<Any>())
    } catch (exception: InvocationTargetException) {
      throw ReflectionException(
        "Error invoking method '${this.methodName}' in ${this.className}",
        exception.cause ?: exception
      )
    } catch (exception: IllegalAccessException) {
      throw ReflectionSecurityException(
        "Illegal access to method '${this.methodName}' in ${this.className}",
        exception
      )
    } finally {
      // CRITICAL: Always restore original accessibility state
      // Prevents security leaks and "illegal reflective access" warnings
      if (!this.originalAccessible) {
        AccessController.setAccessible(this.method, false)
      }
    }
  }
}