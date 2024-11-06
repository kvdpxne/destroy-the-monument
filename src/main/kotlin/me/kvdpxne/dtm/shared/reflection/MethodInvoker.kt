package me.kvdpxne.dtm.shared.reflection

/**
 * Interface for invoking methods reflectively.
 *
 * `MethodInvoker` provides a mechanism to invoke methods on objects dynamically at runtime,
 * using reflection. This can be useful in scenarios where the specific method or class to be
 * called is not known at compile-time, allowing for more flexible and modular code.
 *
 * @since 0.1.0
 */
interface MethodInvoker {

  /**
   * Invokes a method on a specified target object with the given parameters.
   *
   * @param target The target object on which the method will be invoked.
   *               If `null`, it indicates that the method is static and does
   *               not require an instance to be called.
   * @param parameters A variable number of arguments to pass to the method
   *                   being invoked.
   * @return The result of the method invocation, or `null` if the method has
   *         no return value. Any exceptions thrown by the method are
   *         propagated to the caller.
   * @throws IllegalArgumentException If the provided arguments do not match
   *                                  the method's signature.
   * @since 0.1.0
   */
  fun invoke(target: Any?, vararg parameters: Any): Any?
}