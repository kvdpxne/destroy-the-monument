package me.kvdpxne.dtm.game

import me.kvdpxne.dtm.shared.WorldLoaderHelper
import org.bukkit.World
import java.util.UUID

data class ArenaMap(var identifier: UUID, var name: String) {

  fun getWorld(): World? {
    return WorldLoaderHelper.getWorld(name)
  }

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as ArenaMap

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