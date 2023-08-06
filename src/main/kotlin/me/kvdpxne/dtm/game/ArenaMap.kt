package me.kvdpxne.dtm.game

import me.kvdpxne.dtm.shared.WorldLoaderHelper
import org.bukkit.World
import java.util.UUID

data class ArenaMap(var identifier: UUID, var name: String) {

  fun getWorld(): World? {
    return WorldLoaderHelper.getWorld(name)
  }
}