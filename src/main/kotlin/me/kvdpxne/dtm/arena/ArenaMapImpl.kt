package me.kvdpxne.dtm.arena

import me.kvdpxne.dtm.shared.WorldUuid
import me.kvdpxne.dtm.shared.world.WorldLoaderHelper
import org.bukkit.Bukkit
import org.bukkit.World

/**
 * @param name
 * @param identifier
 *
 * @since 0.1.0
 */
class ArenaMapImpl(
  // @formatter:off
  override var name      : String,
  override var identifier: WorldUuid
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

  override fun save(): Boolean {
    return try {
      this.world?.save()
      true
    } catch (_: Exception) {
      false
    }
  }

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as ArenaMapImpl

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
    return "ArenaMap{" +
      "name=\"${this.name}\", " +
      "identifier=\"${this.identifier}\"" +
      "}"
  }
}