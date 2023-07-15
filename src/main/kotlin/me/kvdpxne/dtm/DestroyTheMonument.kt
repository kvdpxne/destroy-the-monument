package me.kvdpxne.dtm

import me.kvdpxne.dtm.command.KitCommand
import org.bukkit.craftbukkit.v1_7_R4.CraftServer
import org.bukkit.plugin.java.JavaPlugin

class DestroyTheMonument : JavaPlugin() {

  override fun onLoad() {
  }

  override fun onEnable() {
    // TODO reflection
    (this.server as CraftServer).commandMap.register("dtm", KitCommand())
  }

  override fun onDisable() {
  }
}