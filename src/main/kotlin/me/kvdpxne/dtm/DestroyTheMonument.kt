package me.kvdpxne.dtm

import com.comphenix.protocol.ProtocolLibrary
import com.comphenix.protocol.ProtocolManager
import com.comphenix.protocol.events.PacketListener
import java.nio.file.Path
import me.kvdpxne.dico.Dico
import me.kvdpxne.dtm.command.CommandManager
import me.kvdpxne.dtm.commands.createBaseCommand
import me.kvdpxne.dtm.commands.createGlobalChatCommand
import me.kvdpxne.dtm.commands.createVoteCommand
import me.kvdpxne.dtm.configuration.ConfigurationManager
import me.kvdpxne.dtm.configuration.GeneralConfiguration
import me.kvdpxne.dtm.data.tasks.AsynchronousUserUpdateTask
import me.kvdpxne.dtm.game.GameManager
import me.kvdpxne.dtm.game.GameService
import me.kvdpxne.dtm.listeners.block.BlockBreakListener
import me.kvdpxne.dtm.listeners.block.BlockBurnListener
import me.kvdpxne.dtm.listeners.block.BlockPistonExtendListener
import me.kvdpxne.dtm.listeners.block.BlockPlaceListener
import me.kvdpxne.dtm.listeners.block.BlockSpreadListener
import me.kvdpxne.dtm.listeners.entity.EntityDamageByBlockListener
import me.kvdpxne.dtm.listeners.entity.EntityDamageByEntityListener
import me.kvdpxne.dtm.listeners.entity.EntityExplodeListener
import me.kvdpxne.dtm.listeners.entity.ProjectileHitListener
import me.kvdpxne.dtm.listeners.entity.ProjectileLaunchListener
import me.kvdpxne.dtm.listeners.packet.PacketPlayInBlockDigListener
import me.kvdpxne.dtm.listeners.packet.PacketPlayInSettingsListener
import me.kvdpxne.dtm.listeners.packet.PacketPlayInWindowClickListener
import me.kvdpxne.dtm.listeners.packet.PacketPlayOutCloseWindowListener
import me.kvdpxne.dtm.listeners.packet.PacketPlayOutEntityDestroyListener
import me.kvdpxne.dtm.listeners.packet.PacketPlayOutTransactionListener
import me.kvdpxne.dtm.listeners.player.PlayerChatListener
import me.kvdpxne.dtm.listeners.player.PlayerCraftItemListener
import me.kvdpxne.dtm.listeners.player.PlayerDeathListener
import me.kvdpxne.dtm.listeners.player.PlayerDropItemListener
import me.kvdpxne.dtm.listeners.player.PlayerFoodLevelChangeListener
import me.kvdpxne.dtm.listeners.player.PlayerInteractListener
import me.kvdpxne.dtm.listeners.player.PlayerInventoryClickListener
import me.kvdpxne.dtm.listeners.player.PlayerInventoryInteractListener
import me.kvdpxne.dtm.listeners.player.PlayerItemConsumeListener
import me.kvdpxne.dtm.listeners.player.PlayerJoinListener
import me.kvdpxne.dtm.listeners.player.PlayerKickListener
import me.kvdpxne.dtm.listeners.player.PlayerLoginListener
import me.kvdpxne.dtm.listeners.player.PlayerPickupItemListener
import me.kvdpxne.dtm.listeners.player.PlayerPrepareCraftItemListener
import me.kvdpxne.dtm.listeners.player.PlayerPrepareItemEnchantListener
import me.kvdpxne.dtm.listeners.player.PlayerQuitListener
import me.kvdpxne.dtm.listeners.player.PlayerRespawnListener
import me.kvdpxne.dtm.listeners.player.PlayerToggleFlightListener
import me.kvdpxne.dtm.listeners.world.WeatherChangeListener
import me.kvdpxne.dtm.profession.ProfessionManager
import me.kvdpxne.dtm.professions.createArcher
import me.kvdpxne.dtm.professions.createAssassin
import me.kvdpxne.dtm.professions.createDefender
import me.kvdpxne.dtm.professions.createEngineer
import me.kvdpxne.dtm.professions.createKnight
import me.kvdpxne.dtm.professions.createMedic
import me.kvdpxne.dtm.professions.createPyro
import me.kvdpxne.dtm.professions.createScout
import me.kvdpxne.dtm.professions.createSpecialist
import me.kvdpxne.dtm.shared.debug.Debug
import me.kvdpxne.dtm.shared.io.Files2
import me.kvdpxne.dtm.shared.reflection.Reflection
import me.kvdpxne.dtm.shared.text.BukkitTextFormatter
import me.kvdpxne.dtm.shared.world.VoidChunkGenerator
import me.kvdpxne.dtm.translation.TranslationService
import me.kvdpxne.dtm.user.LocalUserManager
import me.kvdpxne.dtm.user.User
import me.kvdpxne.dtm.user.UserBuilder
import me.kvdpxne.dtm.user.UserService
import me.kvdpxne.notchity.VersionCreator
import org.bukkit.entity.Player
import org.bukkit.event.Listener
import org.bukkit.generator.ChunkGenerator
import org.bukkit.plugin.PluginManager
import org.bukkit.plugin.java.JavaPlugin

/**
 * Main class for the DestroyTheMonument plugin, extending JavaPlugin.
 *
 * This class handles the initialization and management of the plugin during
 * its lifecycle.
 *
 * It also provides a singleton-like instance of the plugin for global access.
 *
 * @since 0.1.0
 */
class DestroyTheMonument : JavaPlugin() {

  companion object {

    /**
     * Holds the singleton instance of the DestroyTheMonument plugin.
     *
     * This instance can be accessed globally within the plugin, but is set
     * only once during the plugin's initialization.
     *
     * @since 0.1.0
     */
    private var _instance: DestroyTheMonument? = null

    /**
     * @since 0.1.0
     */
    val instance: DestroyTheMonument
      get() {

        return requireNotNull(this._instance) {
          "You cannot access the DTM plugin instance because the plugin " +
            "is currently disabled."
        }
      }
  }

  /**
   * Flag indicating whether the plugin is in the process of being disabled.
   * When set to true, the plugin is either in an unrecoverable error state
   * or about to be shut down.
   *
   * This flag prevents further operations that should not proceed if the
   * plugin is being disabled.
   *
   * @since 0.1.0
   */
  private var isDisabling: Boolean = false

  /**
   * Registers multiple event listeners with the plugin's event handling system.
   *
   * This method accepts any number of listeners and registers them with the
   * server's plugin manager.
   * The listeners will be active once registered, handling corresponding events
   * within the plugin.
   *
   * @param listeners List of event listeners to register.
   *
   * @since 0.1.0
   */
  private fun registerListeners(
    vararg listeners: Listener
  ) {
    val pluginManager: PluginManager = this.server.pluginManager

    for (listener: Listener in listeners) {
      pluginManager.registerEvents(listener, this)
    }
  }

  /**
   * @param listeners
   *
   * @since 0.1.0
   */
  private fun registerPacketListeners(
    vararg listeners: PacketListener
  ) {
    val protocolManager: ProtocolManager = ProtocolLibrary.getProtocolManager()

    for (listener: PacketListener in listeners) {
      protocolManager.addPacketListener(listener)
    }
  }

  /**
   * Shuts down the plugin by disabling it.
   *
   * This method is called when an irrecoverable error occurs during the loading
   * process, ensuring that the plugin is safely disabled to prevent further
   * issues.
   *
   * @since 0.1.0
   */
  private fun shutdown() {
    this.server.pluginManager.disablePlugin(this)
  }

  /**
   * @since 0.1.0
   */
  override fun onLoad() {
    //
    Debug.initialize(this.logger)

    //
    val version: Int = VersionCreator.getBukkitVersion().number

    //
    if (10700 > version || 10900 < version) {
      arrayOf(
        "An error occurred while trying to load the plugin.",
        "Error: Incorrect Minecraft release",
        "",
        "The currently used version of the plugin requires a",
        "Minecraft release codenamed \"v1_7_R4\".",
        "",
        "We recommend using the Spigot server platform with the",
        "code name \"b1657\" to get full compatibility with the",
        "current version of the plugin.",
        "",
        "If you think the error should not occur please contact us.",
        Constants.GITHUB_ISSUES
      ).forEach { message: String ->
        this.logger.severe(message)
      }

      this.isDisabling = true
      return
    }

    //
    val directoryPath: Path = this.dataFolder.toPath()
    Files2.createDirectoryIfNotExists(directoryPath)

    //
    ConfigurationManager.moveConfigurations(directoryPath)

    //
    ConfigurationManager.loadConfigurations(directoryPath)

    //
    TranslationService.loadTranslations()

    //
    _instance = this

    PluginContext.textFormatter = BukkitTextFormatter

    //
    if (GeneralConfiguration.USE_PROTOCOL_LIB) {
      ProtocolLibrary.getProtocolManager()
    } else {
      Reflection
    }

    try {
      GameService
      UserService

      //
      LocalUserManager
      GameManager
    } catch (exception: Throwable) {
      exception.printStackTrace()

      this.isDisabling = true
      return
    }

    CommandManager
    ProfessionManager
  }

  /**
   * @since 0.1.0
   */
  override fun onEnable() {
    // Sprawdzenie, czy plugin jest w trakcie wyłączania.
    // Jeżeli tak, to wywoływana jest metoda shutdown, która wyłącza plugin.
    if (this.isDisabling) {
      this.shutdown()
      return
    }

    // Registers all event listeners built into the server platform needed
    // for the plugin to function properly.
    this.registerListeners(
      BlockBreakListener,
      BlockBurnListener,
      BlockPistonExtendListener,
      BlockPlaceListener,
      BlockSpreadListener,

      EntityDamageByBlockListener,
      EntityDamageByEntityListener,
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
      PlayerLoginListener,
      PlayerPickupItemListener,
      PlayerPrepareCraftItemListener,
      PlayerPrepareItemEnchantListener,
      PlayerQuitListener,
      PlayerRespawnListener,
      PlayerToggleFlightListener,

      ProjectileHitListener,
      ProjectileLaunchListener,

      WeatherChangeListener
    )

    if (GeneralConfiguration.USE_PROTOCOL_LIB) {
      //
      this.registerPacketListeners(
        PacketPlayInBlockDigListener,
        PacketPlayInSettingsListener,
        PacketPlayInWindowClickListener,
        PacketPlayOutCloseWindowListener,
        PacketPlayOutEntityDestroyListener,
        PacketPlayOutTransactionListener,
      )
    }

    //
    CommandManager.addCommands(
      createBaseCommand(),
      createGlobalChatCommand(),
      createVoteCommand()
    )

    ProfessionManager.addProfessions(
      createArcher(),
      createKnight(),
      createEngineer(),
      createScout(),
      createMedic(),
      createPyro(),
      createDefender(),
      createAssassin(),
      createSpecialist()
    )

    for (player: Player in Dico.getLocalPlayers().asCollection()) {
      var user: User? = UserService.findUserByIdentifier(player.uniqueId)

      if (null == user) {
        user = UserBuilder.create(player).build()
        UserService.createUser(user)
      }

      // Dodaje obiekt użytkownika do lokalnej pamięci.
      LocalUserManager.addUser(user)

//      PacketLogger.injectPlayer(player)
    }

    this.server.scheduler.runTaskTimerAsynchronously(
      this,
      AsynchronousUserUpdateTask,
      0L,
      5L * 60L * 20L
    )
  }

  /**
   * @since 0.1.0
   */
  override fun onDisable() {
    if (this.isDisabling) {
      return
    }

    // Usuwa wszystkie przechowywane obiektu użytkowników z lokalnej pamięci.
    LocalUserManager.removeUsers()

    // Usuwa wszystkie przechowywane obiekty gry z lokalnej pamięci.
    GameManager.removeGames()

    //
    if (GeneralConfiguration.USE_PROTOCOL_LIB) {
      ProtocolLibrary.getProtocolManager().removePacketListeners(this)
    }

    Debug.log {
      "The plugin has been properly disabled."
    }

    //
    _instance = null

    //
    Debug.destroy()
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
    if (GeneralConfiguration.OVERRIDE_DEFAULT_CHUNK_GENERATOR) {
      return VoidChunkGenerator.INSTANCE
    }

    return super.getDefaultWorldGenerator(name, identifier)
  }
}