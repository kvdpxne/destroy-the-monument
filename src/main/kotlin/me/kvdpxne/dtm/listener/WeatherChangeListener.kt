package me.kvdpxne.dtm.listener

import me.kvdpxne.dtm.game.ArenaManager
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.weather.WeatherChangeEvent

object WeatherChangeListener : Listener {

  @EventHandler
  fun handleWeatherChange(event: WeatherChangeEvent) {
    event.isCancelled = ArenaManager.getArenas().any {
      it.identifier == event.world.uid
    }
  }
}