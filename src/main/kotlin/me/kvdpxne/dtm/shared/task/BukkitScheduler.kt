package me.kvdpxne.dtm.shared.task

import me.kvdpxne.dtm.DestroyTheMonument
import org.bukkit.Bukkit
import org.bukkit.scheduler.BukkitTask

/**
 * @since 0.1.0
 */
fun runSynchronousTask(
  runnable: Runnable
): BukkitTask {
  return Bukkit.getScheduler().runTask(
    DestroyTheMonument.instance,
    runnable
  )
}

/**
 * @since 0.1.0
 */
fun runSynchronousDelayedTask(
  delay: Long,
  runnable: Runnable
): BukkitTask {
  return Bukkit.getScheduler().runTaskLater(
    DestroyTheMonument.instance,
    runnable,
    delay
  )
}

/**
 * @since 0.1.0
 */
fun runSynchronousDelayedRepeatingTask(
  delay: Long,
  period: Long,
  runnable: Runnable
): BukkitTask {
  return Bukkit.getScheduler().runTaskTimer(
    DestroyTheMonument.instance,
    runnable,
    delay,
    period
  )
}

/**
 * @since 0.1.0
 */
fun runSynchronousRepeatingTask(
  period: Long,
  runnable: Runnable
): BukkitTask {
  return runSynchronousDelayedRepeatingTask(0L, period, runnable)
}

/**
 * @since 0.1.0
 */
fun runAsynchronousTask(
  runnable: Runnable
): BukkitTask {
  return Bukkit.getScheduler().runTaskAsynchronously(
    DestroyTheMonument.instance,
    runnable
  )
}

/**
 * @since 0.1.0
 */
fun runAsynchronousDelayedTask(
  delay: Long,
  runnable: Runnable
): BukkitTask {
  return Bukkit.getScheduler().runTaskLaterAsynchronously(
    DestroyTheMonument.instance,
    runnable,
    delay
  )
}

/**
 * @since 0.1.0
 */
fun runAsynchronousDelayedRepeatingTask(
  delay: Long,
  period: Long,
  runnable: Runnable
): BukkitTask {
  return Bukkit.getScheduler().runTaskTimerAsynchronously(
    DestroyTheMonument.instance,
    runnable,
    delay,
    period
  )
}

/**
 * @since 0.1.0
 */
fun runAsynchronousRepeatingTask(
  period: Long,
  runnable: Runnable
): BukkitTask {
  return runAsynchronousDelayedRepeatingTask(
    0L,
    period,
    runnable
  )
}

/**
 * @since 0.1.0
 */
fun cancelTask(
  identifier: Int
) {
  Bukkit.getScheduler().cancelTask(identifier)
}