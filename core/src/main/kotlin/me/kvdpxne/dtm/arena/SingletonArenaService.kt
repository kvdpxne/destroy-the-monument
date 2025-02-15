package me.kvdpxne.dtm.arena

import me.kvdpxne.dtm.position.monument.MonumentPosition
import me.kvdpxne.dtm.position.revival.RevivalPosition

object SingletonArenaService : ArenaService {

  override fun findArenas(): Collection<Arena> {
    TODO("Not yet implemented")
  }

  override fun findArenaByIdentifierOrNull(identifier: CharSequence?): Arena {
    TODO("Not yet implemented")
  }

  override fun findArenaByIdentifier(identifier: CharSequence?): Arena {
    TODO("Not yet implemented")
  }

  override fun findArenaByNameOrNull(name: CharSequence?): Arena {
    TODO("Not yet implemented")
  }

  override fun findArenaByName(name: CharSequence?): Arena {
    TODO("Not yet implemented")
  }

  override fun insertArena(arena: Arena?) {
    TODO("Not yet implemented")
  }

  override fun insertArenaRevivalPosition(
    arena: Arena?,
    revivalPosition: RevivalPosition?
  ) {
    TODO("Not yet implemented")
  }

  override fun insertArenaMonumentPosition(
    arena: Arena?,
    monumentPosition: MonumentPosition?
  ) {
    TODO("Not yet implemented")
  }

  override fun updateArena(arena: Arena?) {
    TODO("Not yet implemented")
  }

  override fun deleteArenaByIdentifier(identifier: CharSequence?) {
    TODO("Not yet implemented")
  }

  override fun deleteArenas() {
    TODO("Not yet implemented")
  }

  override fun countArenas(): Long {
    TODO("Not yet implemented")
  }
}