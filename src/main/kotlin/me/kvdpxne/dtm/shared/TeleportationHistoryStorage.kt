package me.kvdpxne.dtm.shared

import java.util.UUID
import org.bukkit.Location

object TeleportationHistoryStorage {

  private val history = mutableMapOf<UUID, ArrayDeque<Location>>()

  fun push(identifier: UUID, position: Location) {
    return history.getOrPut(identifier) { ArrayDeque() }.addLast(position)
  }

  fun pop(identifier: UUID): Location? {
    return history[identifier]?.removeLastOrNull()
  }
}