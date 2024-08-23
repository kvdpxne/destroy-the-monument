package me.kvdpxne.dtm

import me.kvdpxne.dico.Dico
import me.kvdpxne.dtm.command.CommandManager
import me.kvdpxne.dtm.commands.createBaseCommand
import me.kvdpxne.dtm.commands.createGlobalChatCommand
import me.kvdpxne.dtm.game.ArenaService
import me.kvdpxne.dtm.game.GameManager
import me.kvdpxne.dtm.listeners.BlockBreakListener
import me.kvdpxne.dtm.listeners.BlockPistonExtendListener
import me.kvdpxne.dtm.listeners.BlockPlaceListener
import me.kvdpxne.dtm.listeners.EntityDamageListener
import me.kvdpxne.dtm.listeners.EntityExplodeListener
import me.kvdpxne.dtm.listeners.PlayerChatListener
import me.kvdpxne.dtm.listeners.PlayerCraftItemListener
import me.kvdpxne.dtm.listeners.PlayerDeathListener
import me.kvdpxne.dtm.listeners.PlayerDropItemListener
import me.kvdpxne.dtm.listeners.PlayerFoodLevelChangeListener
import me.kvdpxne.dtm.listeners.PlayerInteractListener
import me.kvdpxne.dtm.listeners.PlayerInventoryClickListener
import me.kvdpxne.dtm.listeners.PlayerInventoryInteractListener
import me.kvdpxne.dtm.listeners.PlayerItemConsumeListener
import me.kvdpxne.dtm.listeners.PlayerJoinListener
import me.kvdpxne.dtm.listeners.PlayerKickListener
import me.kvdpxne.dtm.listeners.PlayerPrepareCraftItemListener
import me.kvdpxne.dtm.listeners.PlayerPrepareItemEnchantListener
import me.kvdpxne.dtm.listeners.PlayerQuitListener
import me.kvdpxne.dtm.listeners.PlayerRespawnListener
import me.kvdpxne.dtm.listeners.PlayerToggleFlightListener
import me.kvdpxne.dtm.listeners.ProjectileHitListener
import me.kvdpxne.dtm.listeners.WeatherChangeListener
import me.kvdpxne.dtm.profession.ProfessionManager
import me.kvdpxne.dtm.shared.debug.Debug
import me.kvdpxne.dtm.shared.minecraft.bukkit.BukkitTextFormatter
import me.kvdpxne.dtm.user.OfflineUserService
import me.kvdpxne.dtm.user.User
import me.kvdpxne.dtm.user.UserManager
import org.bukkit.entity.Player
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin

@Suppress("unused")
class DestroyTheMonument : JavaPlugin() {

  companion object {
    var instance: DestroyTheMonument? = null
      private set
  }

  init {
    Debug.initialize(this.logger)

//    System.setProperty(org.slf4j.simple.SimpleLogger.DEFAULT_LOG_LEVEL_KEY, "TRACE")
    PluginContext.textFormatter = BukkitTextFormatter

    // Initialize
    GameManager
    ArenaService
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
      BlockBreakListener,
      BlockPistonExtendListener,
      BlockPlaceListener,

      EntityDamageListener,
      EntityExplodeListener,

      PlayerChatListener,
      PlayerCraftItemListener,
      PlayerDeathListener,
      PlayerDropItemListener,
      PlayerFoodLevelChangeListener,
      PlayerInteractListener,
      PlayerInventoryClickListener,
      PlayerInventoryInteractListener,
      PlayerItemConsumeListener,
      PlayerJoinListener,
      PlayerKickListener,
      PlayerPrepareCraftItemListener,
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

    for (player: Player in Dico.getLocalPlayers().asCollection()) {
      //
      val user: User = OfflineUserService.findUserByIdentifier(player.uniqueId)
        ?: OfflineUserService.createUser(
          player.uniqueId,
          player.name
        )

      //
      UserManager.addUser(user)
    }
  }

  override fun onDisable() {
    instance = null
  }
}