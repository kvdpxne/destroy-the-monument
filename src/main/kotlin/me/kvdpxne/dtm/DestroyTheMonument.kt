package me.kvdpxne.dtm

import me.kvdpxne.dtm.command.BaseCommand
import me.kvdpxne.dtm.command.BukkitCommandMapAccessor
import me.kvdpxne.dtm.command.KitCommand
import me.kvdpxne.dtm.gui.GuiActionHandler
import me.kvdpxne.dtm.listener.PlayerJoinListener
import me.kvdpxne.dtm.listener.PlayerQuitListener
import me.kvdpxne.dtm.listener.WeatherChangeListener
import org.bukkit.craftbukkit.v1_7_R4.CraftServer
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin

class DestroyTheMonument : JavaPlugin() {

  private fun registerListener(vararg listeners: Listener) {
    val pluginManager = server.pluginManager
    listeners.forEach {
      pluginManager.registerEvents(it, this)
    }
  }

  override fun onLoad() {
  }

  override fun onEnable() {
    //
    registerListener(
      //
      GuiActionHandler,

      PlayerJoinListener,
      PlayerQuitListener,
      WeatherChangeListener
    )

    BukkitCommandMapAccessor.registerCommands(
      BaseCommand
    )

    // TODO reflection
    (this.server as CraftServer).commandMap.register("dtm", KitCommand())
  }

  override fun onDisable() {
  }
}