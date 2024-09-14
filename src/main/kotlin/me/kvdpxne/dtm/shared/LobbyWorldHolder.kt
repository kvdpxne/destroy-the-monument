package me.kvdpxne.dtm.shared

import java.lang.ref.Reference
import java.lang.ref.WeakReference
import me.kvdpxne.dtm.configuration.Configuration
import org.bukkit.World

object LobbyWorldHolder {

  private var _lobbyWorld: Reference<World> = WeakReference(null)

  val lobbyWorld: World?
    get() {
      var world: World? = this._lobbyWorld.get()
      if (null != world) {
        return world
      }

      world = WorldLoaderHelper.getWorld(Configuration.LOBBY_WORLD_NAME)
      if (null != world) {
        this._lobbyWorld = WeakReference(world)
        return world
      }

      this._lobbyWorld = WeakReference(null)
      return null
    }
}