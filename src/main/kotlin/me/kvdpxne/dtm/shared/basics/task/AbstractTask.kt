package me.kvdpxne.dtm.shared.basics.task

import java.util.logging.Logger
import me.kvdpxne.dtm.shared.minecraft.bukkit.cancelTask

/**
 * Abstract base class for tasks.
 *
 * Provides a default implementation of `Runnable` and handles potential
 * exceptions during task execution. Tasks should override the abstract
 * `execute()` method to define their specific logic.
 *
 * @since 0.1.0
 */
abstract class AbstractTask : Task, Runnable {

  /**
   * The unique identifier of the task.
   *
   * This value is assigned automatically and should not be modified.
   *
   * @since 0.1.0
   */
  final override var identifier: Int = 0
    private set

  /**
   * @since 0.1.0
   */
  final override val isAsynchronous: Boolean
    get() = super.isAsynchronous

  /**
   * Executes the task's logic.
   *
   * This method should be overridden by subclasses to implement the specific
   * task behavior. Any exceptions thrown by this method will be logged.
   *
   * @since 0.1.0
   */
  final override fun run() {
    try {
      this.execute()
    } catch (exception: Exception) {
      Logger.getAnonymousLogger().severe("An error occurred while handling concurrent request")
    }
  }

  /**
   * Cancels the task.
   *
   * This method delegates the cancellation to the `cancelTask` function,
   * passing the task's identifier. The actual cancellation logic is handled by
   * the `cancelTask` function, which is likely responsible for managing a task
   * registry or queue.
   *
   * @since 0.1.0
   */
  final override fun cancel() {
    cancelTask(this.identifier)
  }

  /**
   * Determines equality based on the task's identifier.
   *
   * Two tasks are considered equal if they have the same identifier.
   *
   * @param other The object to compare with.
   * @return `true` if the objects are equal, `false` otherwise.
   *
   * @since 0.1.0
   */
  override fun equals(
    other: Any?
  ): Boolean {
    if (this === other) {
      return true
    }

    if (this.javaClass != other?.javaClass) {
      return false
    }

    other as AbstractTask
    return this.identifier == other.identifier
  }

  /**
   * Returns the hash code based on the task's identifier.
   *
   * @return The hash code of the task.
   *
   * @since 0.1.0
   */
  override fun hashCode(): Int {
    return this.identifier
  }
}