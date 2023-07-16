package me.kvdpxne.dtm.game

import java.util.UUID

object GameManager {

  private val identifierGameMap: MutableMap<UUID, Game>

  init {
    identifierGameMap = linkedMapOf()
  }
}