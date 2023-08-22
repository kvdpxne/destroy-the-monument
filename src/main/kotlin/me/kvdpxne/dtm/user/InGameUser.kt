package me.kvdpxne.dtm.user

import me.kvdpxne.dtm.shared.Position

interface InGameUser<W : Any> {

  @Throws(UserException::class)
  fun getPosition(): Position

  fun isOnline(): Boolean

  fun isHidden(): Boolean

  fun teleport(world: W, position: Position)

  fun teleport(position: Position)
}