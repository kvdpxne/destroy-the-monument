package me.kvdpxne.dtm

import me.kvdpxne.dtm.command.BaseCommand
import me.kvdpxne.dtm.command.bukkit.BukkitCommandMapAccessor
import me.kvdpxne.dtm.game.ArenaManager
import me.kvdpxne.dtm.game.GameManager
import me.kvdpxne.dtm.gui.GuiActionHandler
import me.kvdpxne.dtm.listener.EntityDamageListener
import me.kvdpxne.dtm.listener.MonumentDestroyHandler
import me.kvdpxne.dtm.listener.PlayerDeathListener
import me.kvdpxne.dtm.listener.PlayerDropItemListener
import me.kvdpxne.dtm.listener.PlayerInteractListener
import me.kvdpxne.dtm.listener.PlayerJoinListener
import me.kvdpxne.dtm.listener.PlayerQuitListener
import me.kvdpxne.dtm.listener.PlayerRespawnListener
import me.kvdpxne.dtm.listener.WeatherChangeListener
import me.kvdpxne.dtm.listener.internal.GameStartListener
import me.kvdpxne.dtm.listener.internal.GameStopListener
import me.kvdpxne.dtm.profession.ProfessionManager
import me.kvdpxne.dtm.shared.BukkitTextFormatter
import me.kvdpxne.dtm.user.UserManager
import me.kvdpxne.thrivi.EventManager
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin

val eventManager: EventManager = EventManager()

@Suppress("unused")
class DestroyTheMonument : JavaPlugin() {

  init {
//    System.setProperty(org.slf4j.simple.SimpleLogger.DEFAULT_LOG_LEVEL_KEY, "TRACE")
    PluginContext.textFormatter = BukkitTextFormatter

    // Initialize
    GameManager
    ArenaManager
    UserManager

    ProfessionManager.initializeBuiltInProfessions()
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

      EntityDamageListener,

      MonumentDestroyHandler,
      PlayerDeathListener,
      PlayerDropItemListener,
      PlayerInteractListener,
      PlayerJoinListener,
      PlayerQuitListener,
      PlayerRespawnListener(this),
      WeatherChangeListener
    )

    eventManager.registerListener(GameStartListener)
    eventManager.registerListener(GameStopListener)

    BukkitCommandMapAccessor.registerCommands(
      BaseCommand
    )
  }

  override fun onDisable() {
  }
}