package me.kvdpxne.dtm.arena.voting

import java.util.concurrent.atomic.AtomicInteger
import me.kvdpxne.dtm.arena.Arena
import me.kvdpxne.dtm.shared.debug.Debug
import me.kvdpxne.dtm.user.LocalUser

class ArenaVotingRegistryImpl : ArenaVotingRegistry {

  private val _arenas: MutableMap<Int, ArenaVoting> = HashMap(4)
  private val _counter: AtomicInteger = AtomicInteger()

  override val arenas: Collection<ArenaVoting>
    get() = this._arenas.values.toList()

  override val size: Int
    get() = this._arenas.size

  override val totalVotes: Int
    get() = this._counter.get()

  override fun contains(
    arena: Arena
  ): Boolean {
    for (votingArena: ArenaVoting in this._arenas.values) {
      if (votingArena.arena == arena) {
        return true
      }
    }
    return false
  }

  override fun castVote(identifier: Int, user: LocalUser): Boolean {
    return this._arenas[identifier]?.castVote(user) ?: false
  }

  override fun revokeVote(identifier: Int, user: LocalUser): Boolean {
    return this._arenas[identifier]?.revokeVote(user) ?: false
  }

  override fun revokeVotes(identifier: Int) {
    this._arenas[identifier]?.revokeVotes()
  }

  override fun addArena(
    arena: Arena
  ): Int {
    if (this.contains(arena)) {
      return -1
    }

    val nextIdentifier: Int = this._counter.incrementAndGet()
    val newPool: ArenaVoting = ArenaVotingImpl(nextIdentifier, arena)

    this._arenas[nextIdentifier] = newPool

    Debug.log {
      ""
    }

    return nextIdentifier
  }

  override fun removeArenaByIdentifier(
    identifier: Int
  ): Boolean {
    return null != this._arenas.remove(identifier)
  }

  override fun removeArena(
    arena: Arena
  ): Boolean {
    for (arenaVoting: ArenaVoting in this._arenas.values) {
      if (arenaVoting.arena == arena) {
        return this.removeArenaByIdentifier(arenaVoting.identifier)
      }
    }
    return false
  }

  override fun removeArenas() {
    this._arenas.clear()
    this._counter.set(0)

    Debug.log {
      ""
    }
  }
}