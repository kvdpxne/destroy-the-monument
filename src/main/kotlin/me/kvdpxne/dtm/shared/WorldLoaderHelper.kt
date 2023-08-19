package me.kvdpxne.dtm.shared

import java.io.File
import org.bukkit.Bukkit
import org.bukkit.World
import org.bukkit.WorldCreator

object WorldLoaderHelper {

  fun getWorld(name: String): World? {
    val world = Bukkit.getWorld(name)
    if (null != world) {
      return world
    }

    val file = File(Bukkit.getServer().worldContainer, name)
    if (!file.exists()) {
      return null
    }

    return Bukkit.createWorld(WorldCreator(name))
  }
}