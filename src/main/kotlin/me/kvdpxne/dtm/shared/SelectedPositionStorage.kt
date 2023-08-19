package me.kvdpxne.dtm.shared

import java.util.UUID
import org.bukkit.Location

object SelectedPositionStorage {

  val selectedBlocks: MutableMap<UUID, Location> = mutableMapOf()
}