package me.kvdpxne.dtm.shared.basics.task

/**
 * Abstract base class for asynchronous tasks.
 *
 * Represents an asynchronous task that executes in the background and does not
 * block the current thread.
 *
 * @since 0.1.0
 */
abstract class AbstractAsynchronousTask : AbstractTask() {

  /**
   * Indicates that the task is asynchronous.
   *
   * This property is always `false` for asynchronous tasks.
   *
   * @since 0.1.0
   */
  final override val isSynchronous: Boolean
    get() = false
}