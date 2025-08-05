//package me.kvdpxne.dtm.game.tasks
//
//import kotlin.random.Random
//import me.kvdpxne.dtm.arena.Arena
//import me.kvdpxne.dtm.arena.ArenaManager
//import me.kvdpxne.dtm.arena.voting.ArenaVoting
//import me.kvdpxne.dtm.arena.voting.ArenaVotingRegistry
//import me.kvdpxne.dtm.game.LocalGameImpl
//import me.kvdpxne.dtm.shared.debug.Debug
//import me.kvdpxne.dtm.shared.task.AsynchronousTask
//import me.kvdpxne.dtm.shared.text.toSingleLines
//
//internal class GamePreparationTask internal constructor(
//  private val game: LocalGameImpl
//) : AsynchronousTask() {
//
//  /**
//   * @since 0.1.0
//   */
//  private fun getRandomArena(): Arena {
//    val arena: Arena? = this.game._arenas.values.randomOrNull()
//    requireNotNull(arena) {
//      ""
//    }
//
//    this.game._currentArena = arena
//    return arena
//  }
//
//  /**
//   * @since 0.1.0
//   */
//  private fun getOutvotedArena(): Arena {
//    val votingRegistry: ArenaVotingRegistry? = this.game._votingRegistry
//    requireNotNull(votingRegistry) {
//      "The voting registry of the arena selection cannot be null."
//    }
//
//    if (0 >= votingRegistry.totalVotes) {
//      return this.getRandomArena()
//    }
//
//    var arena: Arena? = null
//    var i = 0
//    for (arenaVoting: ArenaVoting in votingRegistry.arenas) {
//      val votes: Int = arenaVoting.votes
//      if (votes > i || (votes == i && null == arena)) {
//        arena = arenaVoting.arena
//        i = votes
//        continue
//      }
//
//      if (Random.Default.nextBoolean()) {
//        arena = arenaVoting.arena
//        i = votes
//      }
//    }
//
//    requireNotNull(arena) {
//      ""
//    }
//
//    this.game._currentArena = arena
//    return arena
//  }
//
//  override fun execute() {
//    //
//    var arena: Arena? = this.game._currentArena
//    if (null == arena) {
//      //
//      //
//      arena = if (null != this.game._votingRegistry) {
//        this.getOutvotedArena()
//      } else {
//        this.getRandomArena()
//      }
//
//      Debug.log {
//        ""
//      }
//    }
//
//    //
//    ArenaManager.addArena(arena)
//
//    // If all the conditions for the start of the game have been met then the
//    // voting registry for the selection of the arena should be immediately
//    // deleted from local memory.
//    if (null != this.game._votingRegistry) {
//      this.game._votingRegistry?.removeArenas()
//      this.game._votingRegistry = null
//
//      Debug.log {
//        ""
//      }
//    }
//
//    // If the countdown to the start of the game has been completed then the
//    // countdown task to the start of the game should be immediately
//    // deleted from local memory.
//    if (0 >= (this.game.countdownTask?.remainingSeconds ?: 1)) {
//      this.game.countdownTask = null
//
//      Debug.log {
//        """
//          The countdown task to the start of the game with the identifier
//          ${this.identifier} has been deleted from local memory because the
//          game has started.
//        """.toSingleLines()
//      }
//    }
//
//
//  }
//}