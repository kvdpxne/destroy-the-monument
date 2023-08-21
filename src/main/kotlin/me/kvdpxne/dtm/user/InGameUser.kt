package me.kvdpxne.dtm.user

import me.kvdpxne.dtm.shared.Position

interface InGameUser<W : Any> {

  fun teleport(world: W, position: Position)

  fun teleport(position: Position)
}