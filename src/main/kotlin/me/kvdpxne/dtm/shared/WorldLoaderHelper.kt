package me.kvdpxne.dtm.shared

import java.io.File
import org.bukkit.Bukkit
import org.bukkit.World
import org.bukkit.WorldCreator
import org.bukkit.craftbukkit.v1_7_R4.CraftWorld

object WorldLoaderHelper {

  private fun prepareWorld(world: World) {
    world.fullTime = 6000L
    (world as CraftWorld).handle.worldData.setStorm(false)
  }

  fun getWorld(name: String): World? {
    var world = Bukkit.getWorld(name)
    if (null != world) {
      this.prepareWorld(world)
      return world
    }

    val file = File(Bukkit.getServer().worldContainer, name)
    if (!file.exists()) {
      return null
    }

    world = Bukkit.createWorld(WorldCreator(name))
    this.prepareWorld(world)
    return world
  }
}