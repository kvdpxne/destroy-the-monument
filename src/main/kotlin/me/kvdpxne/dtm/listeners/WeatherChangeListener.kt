package me.kvdpxne.dtm.listeners

import me.kvdpxne.dtm.game.Game
import me.kvdpxne.dtm.game.GameManager
import me.kvdpxne.dtm.game.LocalGame
import me.kvdpxne.dtm.shared.minecraft.bukkit.cancel
import org.bukkit.World
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

    // Obiekt światu, na którym zmieniła się pogoda.
    val world: World = event.world

    //
    for (game: Game<*> in GameManager.games) {
      //
      if (game !is LocalGame || !(game.isRunning || game.isStopping)) {
        continue
      }

      //
      if (world != game.currentArena?.map?.world) {
        continue
      }

      //
      event.cancel()
    }
  }
}