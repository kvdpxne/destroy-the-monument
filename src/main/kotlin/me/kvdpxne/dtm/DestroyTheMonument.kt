package me.kvdpxne.dtm

import me.kvdpxne.dtm.command.BaseCommand
import me.kvdpxne.dtm.command.bukkit.BukkitCommandMapAccessor
import me.kvdpxne.dtm.game.ArenaManager
import me.kvdpxne.dtm.game.GameManager
import me.kvdpxne.dtm.gui.GuiActionHandler
import me.kvdpxne.dtm.listener.MonumentDestroyHandler
import me.kvdpxne.dtm.listener.PlayerDropItemListener
import me.kvdpxne.dtm.listener.PlayerInteractListener
import me.kvdpxne.dtm.listener.PlayerJoinListener
import me.kvdpxne.dtm.listener.PlayerQuitListener
import me.kvdpxne.dtm.listener.WeatherChangeListener
import me.kvdpxne.dtm.shared.BukkitTextFormatter
import me.kvdpxne.dtm.user.UserManager
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin

@Suppress("unused")
class DestroyTheMonument : JavaPlugin() {

  init {
    System.setProperty(org.slf4j.simple.SimpleLogger.DEFAULT_LOG_LEVEL_KEY, "TRACE")
    PluginContext.textFormatter = BukkitTextFormatter

    // Initialize
    GameManager
    ArenaManager
    UserManager
  }

  private fun registerListener(vararg listeners: Listener) {
    val pluginManager = server.pluginManager
    listeners.forEach {
      pluginManager.registerEvents(it, this)
    }
  }

  override fun onLoad() {
//    ArenaManager.arenas.values.forEach {
//      println(it.toString())
//      it.spawnPoints.values.forEach {
//        println(it.toString())
//      }
//      it.monuments.values.forEach {
//        it.forEach {
//          println(it.toString())
//        }
//      }
//    }
  }

  override fun onEnable() {
    //
    registerListener(
      //
      GuiActionHandler,

      MonumentDestroyHandler,
      PlayerDropItemListener,
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