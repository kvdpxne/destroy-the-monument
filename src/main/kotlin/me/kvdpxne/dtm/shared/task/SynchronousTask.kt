package me.kvdpxne.dtm.shared.task

/**
 * Abstract base class for synchronous tasks.
 *
 * Represents a synchronous task that executes immediately and blocks the
 * current thread until completion.
 *
 * @since 0.1.0
 */
abstract class SynchronousTask : AbstractTask() {

  /**
   * Indicates that the task is synchronous.
   *
   * This property is always `true` for synchronous tasks.
   *
   * @since 0.1.0
   */
  final override val isSynchronous: Boolean
    get() = true
}