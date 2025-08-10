package me.kvdpxne.dtm.shared.reflection.accessors

/**
 * Interface for **dynamically invoking class constructors** to create new object instances.
 *
 * ### Technical Purpose
 * Provides a standardized way to instantiate classes when:
 * - The target class is determined at runtime
 * - Constructors have non-public visibility
 * - Parameter types are dynamically resolved
 *
 * ### Non-Technical Analogy
 * Think of a constructor as a **3D printer blueprint**. This interface lets you:
 * 1. Select the blueprint (constructor)
 * 2. Provide raw materials (parameters)
 * 3. Press "Print" (invoke) to create a new physical object
 *
 * Without this, you'd need to know the exact blueprint at "compile time" (when writing code).
 *
 * ### Usage Context
 * ```kotlin
 * // Create Player instance via reflection
 * val playerClass = Reflection.getClass("net.minecraft.server.Player")
 * val constructor = Reflection.getConstructor(playerClass)
 * val player = constructor.invoke(parameters = arrayOf(world, position))
 * ```
 *
 * ### Security Notes
 * - Automatically handles Java's access control checks
 * - Restores original accessibility after invocation (prevents security leaks)
 * - Throws [ReflectionSecurityException] for illegal access attempts
 *
 * @see [Reflection.getConstructor] Factory method to obtain implementations
 * @see [CachedConstructorInvoker] Default implementation with access caching
 * @since 0.1.0
 */
interface ConstructorInvoker {

  /**
   * Creates a new instance of the target class by invoking its constructor.
   *
   * ### Parameter Handling
   * - `null` or omitted `parameters` → Invokes no-argument constructor
   * - Non-null array → Matches parameters to constructor signature
   *   - `null` values allowed for nullable parameters
   *   - Primitive types auto-boxed (e.g., `Int` → `java.lang.Integer`)
   *
   * ### Return Behavior
   * - Always returns a valid object instance (never `null`)
   * - Throws exceptions instead of returning error values
   *
   * ### Error Scenarios
   * | Exception                          | Cause                                                                 |
   * |------------------------------------|-----------------------------------------------------------------------|
   * | [ReflectionException]              | Constructor threw an exception during execution                      |
   * | [ReflectionSecurityException]      | Insufficient permissions to access constructor                       |
   * | [ConstructorNotFoundReflectionException] | No matching constructor found in [Reflection.getConstructor]      |
   *
   * @param parameters Optional array of constructor arguments (default = `null`)
   *   - Order must match constructor parameter declaration
   *   - `null` entries allowed for nullable parameters
   *   - Primitive types automatically converted to wrapper classes
   *
   * @return New instance of the target class
   *   - Never `null` (construction failures throw exceptions)
   *   - Type matches the class used to create this invoker
   *
   * @throws [me.kvdpxne.dtm.shared.reflection.ReflectionException]
   *   When the constructor invocation fails due to:
   *   - Invalid parameter types/values
   *   - Exceptions thrown *by* the constructor
   *   - Abstract class instantiation attempts
   *
   * @throws [me.kvdpxne.dtm.shared.reflection.ReflectionSecurityException]
   *   When Java's security manager blocks access to the constructor
   *
   * @since 0.1.0
   */
  fun invoke(
    parameters: Array<Any?>? = null
  ): Any
}