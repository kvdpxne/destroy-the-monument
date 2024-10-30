package me.kvdpxne.dtm.shared.task

import me.kvdpxne.dtm.shared.Cancellable
import me.kvdpxne.dtm.shared.Identifiable

/**
 * Represents a task that can be executed and potentially canceled.
 *
 * A task can be either synchronous or asynchronous. Synchronous tasks execute
 * immediately and block the current thread until completion, while asynchronous
 * tasks execute in the background and do not block the current thread.
 *
 * @since 0.1.0
 */
interface Task : Cancellable, Identifiable<Int> {

  /**
   * Indicates whether the task is synchronous.
   *
   * A synchronous task executes immediately and blocks the current thread
   * until completion.
   *
   * @return `true` if the task is synchronous, `false` otherwise.
   *
   * @since 0.1.0
   */
  val isSynchronous: Boolean

  /**
   * Indicates whether the task is asynchronous.
   *
   * An asynchronous task executes in the background and does not block the
   * current thread.
   *
   * @return `true` if the task is asynchronous, `false` otherwise.
   *
   * @since 0.1.0
   */
  val isAsynchronous: Boolean
    get() = !this.isSynchronous

  /**
   * Executes the task.
   *
   * The behavior of this method depends on the task's type. Synchronous tasks
   * will block the current thread until completion, while asynchronous tasks
   * will return immediately and execute in the background.
   *
   * @since 0.1.0
   */
  fun execute()
}