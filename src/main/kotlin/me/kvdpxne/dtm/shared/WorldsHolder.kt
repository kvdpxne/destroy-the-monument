package me.kvdpxne.dtm.shared

import java.lang.ref.Reference
import java.lang.ref.WeakReference
import me.kvdpxne.dtm.configuration.Configuration
import org.bukkit.World

/**
 * @since 0.1.0
 */
object WorldsHolder {

  /**
   * @since 0.1.0
   */
  private val _lobbyWorld: Reference<World> by lazy {
    WeakReference(WorldLoaderHelper.getWorld(Configuration.LOBBY_WORLD_NAME))
  }

  /**
   * @since 0.1.0
   */
  val lobbyWorld: World?
    get() = this._lobbyWorld.get()
}