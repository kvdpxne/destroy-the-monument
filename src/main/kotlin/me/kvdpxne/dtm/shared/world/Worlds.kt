package me.kvdpxne.dtm.shared.world

import org.bukkit.Bukkit
import org.bukkit.World

object Worlds {

  /**
   * @since 0.1.0
   */
  val localWorldsNames: List<String>
    get() {
      return Bukkit.getServer().worlds
        .map { world: World ->
          world.name
        }
        .toList()
    }

  val worldsNames: List<String>
    get() {
      return emptyList()
    }
}