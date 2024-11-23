package me.kvdpxne.dtm.shared.reflection

/**
 * @since 0.1.0
 */
interface ConstructorInvoker {

  /**
   * @since 0.1.0
   */
  fun invoke(vararg parameters: Any?): Any
}