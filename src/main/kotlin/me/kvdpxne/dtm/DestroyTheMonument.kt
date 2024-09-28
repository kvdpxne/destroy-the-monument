package me.kvdpxne.dtm

import java.util.UUID
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import me.kvdpxne.dico.Dico
import me.kvdpxne.dtm.command.CommandManager
import me.kvdpxne.dtm.commands.createBaseCommand
import me.kvdpxne.dtm.commands.createGlobalChatCommand
import me.kvdpxne.dtm.configuration.Configuration
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
import me.kvdpxne.dtm.shared.VoidChunkGenerator
import me.kvdpxne.dtm.shared.debug.Debug
import me.kvdpxne.dtm.shared.minecraft.bukkit.BukkitTextFormatter
import me.kvdpxne.dtm.user.LocalUserManager
import me.kvdpxne.dtm.user.User
import me.kvdpxne.dtm.user.UserBuilder
import me.kvdpxne.dtm.user.UserService
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer
import org.bukkit.entity.Player
import org.bukkit.event.Listener
import org.bukkit.generator.ChunkGenerator
import org.bukkit.plugin.java.JavaPlugin

@Suppress("unused")
class DestroyTheMonument : JavaPlugin() {

  companion object {

    /**
     * @since 0.1.0
     */
    var instance: DestroyTheMonument? = null
      private set
  }

  private fun registerListener(vararg listeners: Listener) {
    val pluginManager = server.pluginManager
    listeners.forEach {
      pluginManager.registerEvents(it, this)
    }
  }

  override fun onLoad() {
    Debug.initialize(this.logger)

    PluginContext.textFormatter = BukkitTextFormatter

    //
    LocalUserManager
    // Initialize
    GameManager
    ArenaService

    ProfessionManager.addBuiltInProfessions()

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
      val user: User = UserService.findUserByIdentifier(player.uniqueId)
        ?: UserBuilder.create(player.uniqueId, player.name)
          .build()

      // Dodaje obiekt użytkownika do lokalnej pamięci.
      LocalUserManager.addUser(user)
    }
  }

  override fun onDisable() {
    // Usuwa wszystkie przechowywane obiektu użytkowników z lokalnej pamięci.
    LocalUserManager.removeUsers()

    // Usuwa wszystkie przechowywane obiekty gry z lokalnej pamięci.
    GameManager.removeGames()

    instance = null
  }

  /**
   * @param name
   * @param identifier
   *
   * @since 0.1.0
   */
  override fun getDefaultWorldGenerator(
    name: String,
    identifier: String
  ): ChunkGenerator {
    if (Configuration.OVERRIDE_DEFAULT_CHUNK_GENERATOR) {
      return VoidChunkGenerator.INSTANCE
    }

    return super.getDefaultWorldGenerator(name, identifier)
  }
}