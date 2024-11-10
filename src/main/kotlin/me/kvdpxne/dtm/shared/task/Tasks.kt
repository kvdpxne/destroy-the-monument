package me.kvdpxne.dtm.shared.task

import me.kvdpxne.dtm.DestroyTheMonument
import org.bukkit.Bukkit
import org.bukkit.scheduler.BukkitTask

/**
 * Utility object for scheduling synchronous and asynchronous tasks.
 *
 * @since 0.1.0
 */
object Tasks {

  /**
   * Runs a task synchronously.
   *
   * @param task The task to execute synchronously.
   * @return The scheduled BukkitTask with an identifier set for tracking.
   * @since 0.1.0
   */
  fun runSynchronousTask(
    task: AbstractTask
  ): BukkitTask {
    val bukkitTask: BukkitTask = Bukkit.getScheduler().runTask(
      DestroyTheMonument.instance,
      task
    )

    task.identifier = bukkitTask.taskId
    return bukkitTask
  }

  /**
   * Runs a task synchronously after a specified delay.
   *
   * @param delay The delay in ticks before running the task.
   * @param task The task to execute synchronously.
   * @return The scheduled BukkitTask with an identifier set for tracking.
   * @since 0.1.0
   */
  fun runSynchronousDelayedTask(
    delay: Long,
    task: AbstractTask
  ): BukkitTask {
    val bukkitTask: BukkitTask = Bukkit.getScheduler().runTaskLater(
      DestroyTheMonument.instance,
      task,
      delay
    )

    task.identifier = bukkitTask.taskId
    return bukkitTask
  }

  /**
   * Runs a task synchronously with an initial delay and a repeating interval.
   *
   * @param delay The initial delay in ticks before the task first executes.
   * @param period The interval in ticks between successive executions.
   * @param task The task to execute synchronously in repeated intervals.
   * @return The scheduled BukkitTask with an identifier set for tracking.
   * @since 0.1.0
   */
  fun runSynchronousDelayedRepeatingTask(
    delay: Long,
    period: Long,
    task: AbstractTask
  ): BukkitTask {
    val bukkitTask: BukkitTask = Bukkit.getScheduler().runTaskTimer(
      DestroyTheMonument.instance,
      task,
      delay,
      period
    )

    task.identifier = bukkitTask.taskId
    return bukkitTask
  }

  /**
   * Runs a task synchronously with a repeating interval and no initial delay.
   *
   * @param period The interval in ticks between successive executions.
   * @param task The task to execute synchronously in repeated intervals.
   * @return The scheduled BukkitTask with an identifier set for tracking.
   * @since 0.1.0
   */
  fun runSynchronousRepeatingTask(
    period: Long,
    task: AbstractTask
  ): BukkitTask {
    return this.runSynchronousDelayedRepeatingTask(
      0L,
      period,
      task
    )
  }

  /**
   * Runs a task asynchronously.
   *
   * @param task The task to execute asynchronously.
   * @return The scheduled BukkitTask with an identifier set for tracking.
   * @since 0.1.0
   */
  fun runAsynchronousTask(
    task: AbstractTask
  ): BukkitTask {
    val bukkitTask: BukkitTask = Bukkit.getScheduler().runTaskAsynchronously(
      DestroyTheMonument.instance,
      task
    )

    task.identifier = bukkitTask.taskId
    return bukkitTask
  }

  /**
   * Runs a task asynchronously after a specified delay.
   *
   * @param delay The delay in ticks before running the task.
   * @param task The task to execute asynchronously.
   * @return The scheduled BukkitTask with an identifier set for tracking.
   * @since 0.1.0
   */
  fun runAsynchronousDelayedTask(
    delay: Long,
    task: AbstractTask
  ): BukkitTask {
    val bukkitTask: BukkitTask = Bukkit.getScheduler().runTaskLaterAsynchronously(
      DestroyTheMonument.instance,
      task,
      delay
    )

    task.identifier = bukkitTask.taskId
    return bukkitTask
  }

  /**
   * Runs a task asynchronously with an initial delay and a repeating interval.
   *
   * @param delay The initial delay in ticks before the task first executes.
   * @param period The interval in ticks between successive executions.
   * @param task The task to execute asynchronously in repeated intervals.
   * @return The scheduled BukkitTask with an identifier set for tracking.
   * @since 0.1.0
   */
  fun runAsynchronousDelayedRepeatingTask(
    delay: Long,
    period: Long,
    task: AbstractTask
  ): BukkitTask {
    val bukkitTask: BukkitTask = Bukkit.getScheduler().runTaskTimerAsynchronously(
      DestroyTheMonument.instance,
      task,
      delay,
      period
    )

    task.identifier = bukkitTask.taskId
    return bukkitTask
  }

  /**
   * Runs a task asynchronously with a repeating interval and no initial delay.
   *
   * @param period The interval in ticks between successive executions.
   * @param task The task to execute asynchronously in repeated intervals.
   * @return The scheduled BukkitTask with an identifier set for tracking.
   * @since 0.1.0
   */
  fun runAsynchronousRepeatingTask(
    period: Long,
    task: AbstractTask
  ): BukkitTask {
    return this.runAsynchronousDelayedRepeatingTask(
      0L,
      period,
      task
    )
  }
}