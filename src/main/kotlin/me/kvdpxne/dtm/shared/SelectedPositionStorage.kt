package me.kvdpxne.dtm.shared

import org.bukkit.Location
import java.util.UUID

object SelectedPositionStorage {

  val selectedBlocks: MutableMap<UUID, Location> = mutableMapOf()
}