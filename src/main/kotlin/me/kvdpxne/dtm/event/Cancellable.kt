package me.kvdpxne.dtm.event

/**
 * @since 0.1.0
 */
interface Cancellable {

  /**
   * @since 0.1.0
   */
  val isCancelled: Boolean

  /**
   * @since 0.1.0
   */
  fun cancel()
}