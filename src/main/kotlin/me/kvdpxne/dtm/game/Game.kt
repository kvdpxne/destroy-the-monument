package me.kvdpxne.dtm.game

import java.time.Instant

class Game(val arena: Arena) {

  private val startInstant: Instant

  init {
    startInstant = Instant.now()
  }
}