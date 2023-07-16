package me.kvdpxne.dtm

import me.kvdpxne.dtm.command.KitCommand
import me.kvdpxne.dtm.gui.GuiActionHandler
import org.bukkit.craftbukkit.v1_7_R4.CraftServer
import org.bukkit.plugin.java.JavaPlugin

class DestroyTheMonument : JavaPlugin() {

  override fun onLoad() {
  }

  override fun onEnable() {
    this.server.pluginManager.registerEvents(GuiActionHandler, this)

    // TODO reflection
    (this.server as CraftServer).commandMap.register("dtm", KitCommand())
  }

  override fun onDisable() {
  }
}