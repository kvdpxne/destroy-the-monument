package me.kvdpxne.dtm.shared.reflection.accessors

/**
 * Interface for **dynamically invoking class methods** at runtime.
 *
 * ### Technical Purpose
 * Enables calling methods when:
 * - The target method is determined at runtime
 * - Method visibility is non-public
 * - Parameter/return types are dynamically resolved
 * - Working across different Java/Minecraft versions
 *
 * ### Non-Technical Analogy
 * Methods are like **machine buttons**:
 * - `invoke()` = Pressing the button with specific settings (parameters)
 * - `target` = Which machine to operate (instance vs class-level)
 * This interface provides a universal remote that works even if:
 * - The button is hidden (`private`)
 * - The machine model varies (version compatibility)
 * - You don't know the button layout beforehand
 *
 * ### Usage Context
 * ```kotlin
 * // Call sendTitle() on player (may differ across Minecraft versions)
 * val player = Bukkit.getPlayer("Steve")
 * val sendTitle = Reflection.getMethod(
 *   player::class.java,
 *   "sendTitle",
 *   parameterTypes = arrayOf(String::class.java, String::class.java)
 * )
 * sendTitle.invoke(player, arrayOf("Welcome", "to the server"))
 * ```
 *
 * ### Security Notes
 * - Automatically handles Java's access control checks
 * - Restores original accessibility after invocation
 * - Throws [ReflectionSecurityException] for illegal access
 *
 * @see [Reflection.getMethod] Factory method to obtain implementations
 * @see [Reflection.invokeMethod] Convenience method for direct invocation
 * @see [CachedMethodInvoker] Default implementation with access caching
 * @since 0.1.0
 */
interface MethodInvoker {

  /**
   * Executes the target method with specified parameters.
   *
   * ### Target Context
   * - For **instance methods**: `target` must be a valid object instance
   * - For **static methods**: `target` must be `null`
   *
   * ### Parameter Handling
   * - `null` or omitted `parameters` → Method expects no arguments
   * - Non-null array → Matches parameters to method signature
   *   - `null` values allowed for nullable parameters
   *   - Primitive types auto-boxed (e.g., `Int` → `java.lang.Integer`)
   *
   * ### Return Behavior
   * - `null` returned when:
   *   - Method has `void` return type
   *   - Method returns `null` explicitly
   *   - Return type is nullable and value is absent
   * - Boxed primitives for primitive return types (e.g., `Int` → `java.lang.Integer`)
   *
   * ### Error Scenarios
   * | Exception                          | Cause                                                                 |
   * |------------------------------------|-----------------------------------------------------------------------|
   * | [ReflectionException]              | Method threw an exception during execution                           |
   * | [ReflectionSecurityException]      | Insufficient permissions to access method                            |
   * | [MethodNotFoundReflectionException]| No matching method found in [Reflection.getMethod]                 |
   *
   * @param target Optional object instance (default = `null`)
   *   - Required for instance methods
   *   - Required to be `null` for static methods
   * @param parameters Optional array of method arguments (default = `null`)
   *   - Order must match method parameter declaration
   *   - `null` entries allowed for nullable parameters
   *   - Primitive types automatically converted to wrapper classes
   *
   * @return Method return value, or `null` if:
   *   - Method has `void` return type
   *   - Method returns `null`
   *   - Return type is nullable and value is absent
   *
   * @throws [ReflectionException]
   *   When method invocation fails due to:
   *   - Invalid parameter types/values
   *   - Exceptions thrown *by* the method
   *   - Signature mismatch (checked via [Reflection.getMethod])
   *
   * @throws [ReflectionSecurityException]
   *   When Java's security manager blocks method access
   *   - Common for `private` methods in security-restricted environments
   *
   * @sample me.kvdpxne.dtm.shared.reflection.samples.MethodSamples.sendPlayerTitle
   * @since 0.1.0
   */
  fun invoke(
    target: Any? = null,
    parameters: Array<Any?>? = null
  ): Any?
}