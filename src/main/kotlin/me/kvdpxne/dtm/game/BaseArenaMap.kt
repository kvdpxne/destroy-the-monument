package me.kvdpxne.dtm.game

import java.util.UUID
import me.kvdpxne.dtm.shared.WorldLoaderHelper
import org.bukkit.Bukkit
import org.bukkit.World

/**
 * @param name
 * @param identifier
 *
 * @since 0.1.0
 */
class BaseArenaMap(
  // @formatter:off
  override var name      : String,
  override var identifier: String
  // @formatter:on
) : ArenaMap {

  override var world: World? = null
    get() {
      if (null != field) {
        return field
      }

      load()
      return field
    }

  override val isLoaded: Boolean
    get() = null != this.world

  override fun load() {
    world = WorldLoaderHelper.getWorld(name)?.let {
      it.isAutoSave = false
      it
    }
  }

  /**
   * @throws IllegalArgumentException If the arena map is not currently loaded
   * and an attempt has been made to unload it.
   */
  override fun unload(): Boolean {
    requireNotNull(world) {
      "Arena map cannot be unloaded if it is not currently loaded."
    }

    world?.players?.forEach {
      it.teleport(Bukkit.getWorlds()[0].spawnLocation)
    }

    return Bukkit.unloadWorld(world, false).also {
      if (it) {
        world = null
      }
    }
  }

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as BaseArenaMap

    if (identifier != other.identifier) return false
    if (name != other.name) return false

    return true
  }

  override fun hashCode(): Int {
    var result = identifier.hashCode()
    result = 31 * result + name.hashCode()
    return result
  }


  override fun toString(): String {
    return "ArenaMap(identifier=$identifier, name='$name')"
  }
}