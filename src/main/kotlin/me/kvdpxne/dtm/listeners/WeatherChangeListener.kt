package me.kvdpxne.dtm.listeners

import me.kvdpxne.dtm.game.GameManager
import me.kvdpxne.dtm.shared.minecraft.bukkit.cancel
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.weather.WeatherChangeEvent

/**
 * @since 0.1.0
 */
object WeatherChangeListener : Listener {

  /**
   * @since 0.1.0
   */
  @EventHandler(
    priority = EventPriority.LOWEST
  )
  fun handleWeatherChange(
    event: WeatherChangeEvent
  ) {
    if (event.isCancelled) {
      return
    }

    //
    val world = event.world

    //
    val worlds = GameManager.games
      .filter {
        //
        it.isRunning || it.isStopping
      }
      .map {
        //
        it.currentArena!!.map!!.world!!
      }

    //
    if (!worlds.contains(world)) {
      return
    }

    //
    event.cancel()
  }
}