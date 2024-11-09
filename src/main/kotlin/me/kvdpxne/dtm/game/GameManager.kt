package me.kvdpxne.dtm.game

import me.kvdpxne.dtm.arena.Arena
import me.kvdpxne.dtm.shared.GameUuid
import me.kvdpxne.dtm.shared.debug.Debug
import me.kvdpxne.dtm.team.Team
import me.kvdpxne.dtm.user.LocalUser

object GameManager {

  /**
   * @since 0.1.0
   */
  private val _games: MutableMap<GameUuid, Game<Team>> = mutableMapOf()

  init {
    // TODO Delete in the future.
    // Information about games should be loaded into memory only when it is
    // really needed and removed when it is no longer needed.
    for (game: Game<Team> in GameService.findGames()) {
      _games[game.identifier] = game.toLocalGame() as Game<Team>
    }
  }

  /**
   * @since 0.1.0
   */
  val games: List<Game<*>>
    get() = _games.values.toList()

  /**
   * @since 0.1.0
   */
  val size: Int
    get() = _games.size

  /**
   * Tries to find a [Game] by [Game.identifier].
   */
  fun findGameByIdentifier(
    identifier: GameUuid
  ): Game<*>? {
    return _games[identifier]
  }

  fun findGameByArena(identifier: GameUuid): Collection<LocalGame> {
    return this._games.values
      .map { it as LocalGame }
      .filter { it.currentArena?.identifier == identifier }
  }

  /**
   * Tries to find a [Game] by [Game.name].
   */
  fun <T : Team, G : Game<T>> findGameByName(
    name: String
  ): G? {
    return _games.values.find {
      it.name.equals(name, true)
    } as G
  }

  /**
   *
   */
  fun <T : Team, G : Game<T>> findByUser(user: LocalUser): G? {
    return _games.values.find {
      if (it is LocalGame) {
        return@find it.isInGame(user)
      }
      return@find false
    } as G?
  }

  fun addArenaToGame(
    game: Game<Team>,
    arena: Arena
  ) {
    val foundGame: Game<Team> = _games[game.identifier]
      ?: return

    foundGame as GameImpl<Team>
    foundGame.addArena(arena)

    Debug.log {
      ""
    }
  }

  /**
   * @since 0.1.0
   */
  fun removeGames() {
    _games.clear()

    Debug.log {
      "All stored game objects have been cleared."
    }
  }
}