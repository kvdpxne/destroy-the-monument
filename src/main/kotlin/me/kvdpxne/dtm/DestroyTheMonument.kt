package me.kvdpxne.dtm

import me.kvdpxne.dtm.command.CommandManager
import me.kvdpxne.dtm.commands.createBaseCommand
import me.kvdpxne.dtm.commands.createGlobalChatCommand
import me.kvdpxne.dtm.game.ArenaManager
import me.kvdpxne.dtm.game.GameManager
import me.kvdpxne.dtm.gui.GuiActionHandler
import me.kvdpxne.dtm.listener.BlockBreakListener
import me.kvdpxne.dtm.listener.BlockPlaceListener
import me.kvdpxne.dtm.listener.EntityDamageListener
import me.kvdpxne.dtm.listener.PlayerChatListener
import me.kvdpxne.dtm.listener.PlayerDeathListener
import me.kvdpxne.dtm.listener.PlayerDropItemListener
import me.kvdpxne.dtm.listener.PlayerFoodLevelChangeListener
import me.kvdpxne.dtm.listener.PlayerInteractListener
import me.kvdpxne.dtm.listener.PlayerItemConsumeListener
import me.kvdpxne.dtm.listener.PlayerJoinListener
import me.kvdpxne.dtm.listener.PlayerPrepareItemEnchantListener
import me.kvdpxne.dtm.listener.PlayerQuitListener
import me.kvdpxne.dtm.listener.PlayerRespawnListener
import me.kvdpxne.dtm.listener.PlayerToggleFlightListener
import me.kvdpxne.dtm.listener.ProjectileHitListener
import me.kvdpxne.dtm.listener.WeatherChangeListener
import me.kvdpxne.dtm.profession.ProfessionManager
import me.kvdpxne.dtm.shared.BukkitTextFormatter
import me.kvdpxne.dtm.user.UserManager
import me.kvdpxne.thrivi.EventManager
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin

val eventManager: EventManager = EventManager()

@Suppress("unused")
class DestroyTheMonument : JavaPlugin() {

  companion object {
    var instance: DestroyTheMonument? = null
      private set
  }

  init {
//    System.setProperty(org.slf4j.simple.SimpleLogger.DEFAULT_LOG_LEVEL_KEY, "TRACE")
    PluginContext.textFormatter = BukkitTextFormatter

    // Initialize
    GameManager
    ArenaManager
    UserManager

    ProfessionManager.addBuiltInProfessions()
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
    instance = this
  }

  override fun onEnable() {
    //
    registerListener(
      //
      GuiActionHandler,

      EntityDamageListener,

      BlockBreakListener,
      BlockPlaceListener,
      PlayerChatListener,
      PlayerDeathListener,
      PlayerDropItemListener,
      PlayerFoodLevelChangeListener,
      PlayerInteractListener,
      PlayerItemConsumeListener,
      PlayerJoinListener,
      PlayerPrepareItemEnchantListener,
      PlayerQuitListener,
      PlayerRespawnListener,
      PlayerToggleFlightListener,
      ProjectileHitListener,
      WeatherChangeListener
    )

    //
    CommandManager.addCommands(
      createBaseCommand(),
      createGlobalChatCommand()
    )
  }

  override fun onDisable() {
    instance = null
  }
}