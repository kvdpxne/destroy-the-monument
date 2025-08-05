package me.kvdpxne.dtm.game

import java.util.Collections
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap
import me.kvdpxne.dtm.arena.Arena
import me.kvdpxne.dtm.configuration.AdvancedConfiguration
import me.kvdpxne.dtm.shared.ArenaUuid
import me.kvdpxne.dtm.shared.GameUuid
import me.kvdpxne.dtm.shared.debug.Debug
import me.kvdpxne.dtm.team.Team
import me.kvdpxne.dtm.user.LocalUser
import org.jetbrains.annotations.UnmodifiableView

/**
 * Manages all active games in the system.
 * Handles game creation, lookup, and cleanup operations.
 *
 * @since 0.1.0
 */
object GameManager {

  /**
   * Internal storage for games using their unique identifiers.
   * Initialized on first access with capacity based on configuration.
   *
   * @since 0.1.0
   */
  private val gamesByIdentifierDelegate: Lazy<ConcurrentMap<GameUuid, Game<Team>>> = lazy {
    ConcurrentHashMap(AdvancedConfiguration.GAME_INITIAL_CAPACITY)
  }

  /**
   * Internal storage for games using their names (case-insensitive).
   * Initialized on first access with capacity based on configuration.
   *
   * @since 0.1.0
   */
  private val gamesByNameDelegate: Lazy<ConcurrentMap<String, Game<Team>>> = lazy {
    ConcurrentHashMap(AdvancedConfiguration.GAME_INITIAL_CAPACITY)
  }

  /**
   * Direct access to games by unique identifier.
   *
   * @since 0.1.0
   */
  private val gamesByIdentifier: ConcurrentMap<GameUuid, Game<Team>> by this.gamesByIdentifierDelegate

  /**
   * Direct access to games by name (lowercase for case-insensitive matching).
   *
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
   * Provides read-only access to all active games.
   * Returns empty collection if manager isn't initialized.
   *
   * @return Unmodifiable collection of all games
   * @since 0.1.0
   */
  val games: @UnmodifiableView Collection<Game<Team>>
    get() {
      if (this.gamesByIdentifierDelegate.isInitialized()) {
        return Collections.unmodifiableCollection(this.gamesByIdentifier.values)
      }
      return Collections.emptyList()
    }

  /**
   * Gets the current number of active games.
   * Returns 0 if manager isn't initialized.
   *
   * @return Number of active games
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
   * Checks if the game manager is ready for use.
   *
   * @return true if both game storage systems are initialized
   * @since 0.1.0
   */
  val initialized: Boolean
    get() = this.gamesByIdentifierDelegate.isInitialized()
      && this.gamesByNameDelegate.isInitialized()

  /**
   * Finds a game by its unique identifier without throwing errors.
   *
   * @param identifier Unique game ID to search for
   * @return Game if found, null otherwise
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
   * Finds a game by its unique identifier.
   *
   * @param identifier Unique game ID to search for
   * @return Game if found
   * @throws GameNotFoundException if game doesn't exist
   * @since 0.1.0
   */
  fun findGameByIdentifier(
    identifier: GameUuid
  ): LocalGame {
    return this.findGameByIdentifierOrNull(identifier)
      ?: throw GameNotFoundException()
  }

  /**
   * Finds a game by its name (case-insensitive) without throwing errors.
   *
   * @param name Game name to search for
   * @return Game if found, null otherwise
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
   * Finds a game by its name (case-insensitive).
   *
   * @param name Game name to search for
   * @return Game if found
   * @throws GameNotFoundException if game doesn't exist
   * @since 0.1.0
   */
  fun findGameByName(
    name: String
  ): LocalGame {
    return this.findGameByNameOrNull(name)
      ?: throw GameNotFoundException()
  }

  /**
   * Finds all games using a specific arena.
   *
   * @param identifier Arena ID to search for
   * @return Collection of games using this arena (may be empty)
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
   * Finds which game a player is currently participating in.
   *
   * @param user Player to search for
   * @return Game if user is playing, null otherwise
   * @since 0.1.0
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

  /**
   * Adds an arena to an existing game.
   *
   * @param game Game to modify
   * @param arena Arena to add
   * @since 0.1.0
   */
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
   * Clears all active games from memory.
   * Does nothing if manager isn't initialized.
   *
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