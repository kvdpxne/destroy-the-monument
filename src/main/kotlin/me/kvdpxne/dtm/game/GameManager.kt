package me.kvdpxne.dtm.game

import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap
import me.kvdpxne.dtm.arena.Arena
import me.kvdpxne.dtm.configuration.AdvancedConfiguration
import me.kvdpxne.dtm.shared.ArenaUuid
import me.kvdpxne.dtm.shared.GameUuid
import me.kvdpxne.dtm.shared.debug.Debug
import me.kvdpxne.dtm.team.Team
import me.kvdpxne.dtm.user.LocalUser

/**
 * @since 0.1.0
 */
object GameManager {

  /**
   * @since 0.1.0
   */
  private val gamesByIdentifierDelegate: Lazy<ConcurrentMap<GameUuid, Game<Team>>> = lazy {
    ConcurrentHashMap(AdvancedConfiguration.GAME_INITIAL_CAPACITY)
  }

  /**
   * @since 0.1.0
   */
  private val gamesByNameDelegate: Lazy<ConcurrentMap<String, Game<Team>>> = lazy {
    ConcurrentHashMap(AdvancedConfiguration.GAME_INITIAL_CAPACITY)
  }

  /**
   * @since 0.1.0
   */
  private val gamesByIdentifier: ConcurrentMap<GameUuid, Game<Team>> by this.gamesByIdentifierDelegate

  /**
   * @since 0.1.0
   */
  private val gamesByName: ConcurrentMap<String, Game<Team>> by this.gamesByNameDelegate

  init {
    // TODO Delete in the future.
    // Information about games should be loaded into memory only when it is
    // really needed and removed when it is no longer needed.
    for (game: Game<Team> in GameService.findGames()) {
      gamesByIdentifier[game.identifier] = game.toLocalGame() as Game<Team>
    }
  }

  /**
   * @since 0.1.0
   */
  val games: List<Game<Team>>
    get() {
      if (this.gamesByIdentifierDelegate.isInitialized()) {
        return this.gamesByIdentifier.values.toList()
      }

      return emptyList()
    }

  /**
   * @since 0.1.0
   */
  val size: Int
    get() {
      if (this.gamesByIdentifierDelegate.isInitialized()) {
        return this.gamesByIdentifier.size
      }

      return 0
    }

  /**
   * @since 0.1.0
   */
  val initialized: Boolean
    get() = this.gamesByIdentifierDelegate.isInitialized()
      && this.gamesByNameDelegate.isInitialized()

  /**
   * @param identifier
   *
   * @since 0.1.0
   */
  fun findGameByIdentifierOrNull(
    identifier: GameUuid
  ): LocalGame? {
    if (this.gamesByIdentifierDelegate.isInitialized()) {
      return this.gamesByIdentifier[identifier] as LocalGame?
    }

    return null
  }

  /**
   * Tries to find a [Game] by [Game.identifier].
   *
   * @param identifier
   * @throws GameNotFoundException
   * @since 0.1.0
   */
  fun findGameByIdentifier(
    identifier: GameUuid
  ): LocalGame {
    return this.findGameByIdentifierOrNull(identifier)
      ?: throw GameNotFoundException()
  }

  /**
   * @since 0.1.0
   */
  fun findGameByNameOrNull(
    name: String
  ): LocalGame? {
    if (this.gamesByNameDelegate.isInitialized()) {
      return this.gamesByName[name.lowercase()] as LocalGame?
    }

    return null
  }

  /**
   * @since 0.1.0
   */
  fun findGameByName(
    name: String
  ): LocalGame {
    return this.findGameByNameOrNull(name)
      ?: throw GameNotFoundException()
  }

  /**
   * @since 0.1.0
   */
  fun findGameByArena(
    identifier: ArenaUuid
  ): Collection<LocalGame> {
    if (!this.gamesByIdentifierDelegate.isInitialized()) {
      return emptyList()
    }

    return buildSet(this.size) {
      for (game: Game<Team> in this@GameManager.gamesByIdentifier.values) {
        if (game !is LocalGame) {
          continue
        }

        if (identifier == game.currentArena?.identifier) {
          this@buildSet.add(game)
        }
      }
    }
  }

  /**
   *
   */
  fun findByUser(
    user: LocalUser
  ): LocalGame? {
    if (!this.gamesByIdentifierDelegate.isInitialized()) {
      return null
    }

    return gamesByIdentifier.values.find {
      if (it is LocalGame) {
        return@find it.isInGame(user)
      }
      return@find false
    } as LocalGame?
  }

  fun addArenaToGame(
    game: Game<Team>,
    arena: Arena
  ) {
    val foundGame: Game<Team> = gamesByIdentifier[game.identifier]
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
    if (!this.initialized) {
      return
    }

    this.gamesByIdentifier.clear()
    this.gamesByName.clear()

    Debug.log {
      "All stored game objects have been cleared."
    }
  }
}