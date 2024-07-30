package me.kvdpxne.dtm.shared.ancillary

/**
 * Represents an object that can be canceled.
 *
 * Classes implementing this interface should provide a mechanism to stop or
 * terminate an ongoing operation. The cancellation process might not be
 * immediate, and the implementation should define the behavior accordingly.
 *
 * @since 0.1.0
 */
interface Cancellable {

  /**
   * Attempts to cancel the ongoing operation.
   *
   * The behavior of this method depends on the implementation. It might be
   * synchronous or asynchronous, and the cancellation might be immediate or
   * delayed.
   *
   * @since 0.1.0
   */
  fun cancel()
}