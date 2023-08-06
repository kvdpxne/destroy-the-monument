package me.kvdpxne.dtm

import me.kvdpxne.dtm.command.BaseCommand
import me.kvdpxne.dtm.command.bukkit.BukkitCommandMapAccessor
import me.kvdpxne.dtm.gui.GuiActionHandler
import me.kvdpxne.dtm.listener.PlayerInteractListener
import me.kvdpxne.dtm.listener.PlayerJoinListener
import me.kvdpxne.dtm.listener.PlayerQuitListener
import me.kvdpxne.dtm.listener.WeatherChangeListener
import me.kvdpxne.dtm.shared.BukkitTextFormatter
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin

@Suppress("unused")
class DestroyTheMonument : JavaPlugin() {

  init {
    PluginContext.textFormatter = BukkitTextFormatter
  }

  private fun registerListener(vararg listeners: Listener) {
    val pluginManager = server.pluginManager
    listeners.forEach {
      pluginManager.registerEvents(it, this)
    }
  }

  override fun onLoad() {
    System.setProperty(org.slf4j.simple.SimpleLogger.DEFAULT_LOG_LEVEL_KEY, "TRACE")
  }

  override fun onEnable() {
    //
    registerListener(
      //
      GuiActionHandler,

      PlayerInteractListener,
      PlayerJoinListener,
      PlayerQuitListener,
      WeatherChangeListener
    )

    BukkitCommandMapAccessor.registerCommands(
      BaseCommand
    )
  }

  override fun onDisable() {
  }
}