package me.kvdpxne.dtm.shared

import org.bukkit.Location
import java.util.UUID

object TeleportationHistoryStorage {

  private val history = mutableMapOf<UUID, ArrayDeque<Location>>()

  fun push(identifier: UUID, position: Location) {
    return history.getOrPut(identifier) { ArrayDeque() }.addLast(position)
  }

  fun pop(identifier: UUID): Location? {
    return history[identifier]?.removeLastOrNull()
  }
}