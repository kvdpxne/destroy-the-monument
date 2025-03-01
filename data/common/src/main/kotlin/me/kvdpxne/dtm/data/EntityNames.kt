package me.kvdpxne.dtm.data

/**
 * A singleton object that contains constant string values representing entity
 * names used in the database and local file system. These entity names are
 * utilized when interacting with the data layer of the plugin.
 *
 * @since 0.1.0
 */
object EntityNames {

  /**
   * Represents the entity name for an arena.
   *
   * @since 0.1.0
   */
  const val ARENA: String = "arena"

  /**
   * Represents the entity name for an arena map.
   *
   * @since 0.1.0
   */
  const val ARENA_MAP: String = "arena_map"

  /**
   * Represents the entity name for monument positions within an arena.
   *
   * @since 0.1.0
   */
  const val ARENA_MONUMENT_POSITIONS: String = "arena_monument_positions"

  /**
   * Represents the entity name for revival positions within an arena.
   *
   * @since 0.1.0
   */
  const val ARENA_REVIVAL_POSITIONS: String = "arena_revival_positions"

  /**
   * Represents the entity name for a game.
   *
   * @since 0.1.0
   */
  const val GAME: String = "game"

  /**
   * Represents the entity name for game arenas.
   *
   * @since 0.1.0
   */
  const val GAME_ARENAS: String = "game_arenas"

  /**
   * Represents the entity name for game teams.
   *
   * @since 0.1.0
   */
  const val GAME_TEAMS: String = "game_teams"

  /**
   * Represents the entity name for a monument position.
   *
   * @since 0.1.0
   */
  const val MONUMENT_POSITION: String = "monument_position"

  /**
   * Represents the entity name for a revival position.
   *
   * @since 0.1.0
   */
  const val REVIVAL_POSITION: String = "revival_position"

  /**
   * Represents the entity name for a team.
   *
   * @since 0.1.0
   */
  const val TEAM: String = "team"

  /**
   * Represents the entity name for a user.
   *
   * @since 0.1.0
   */
  const val USER: String = "user"

  /**
   * Represents the entity name for user statistics.
   *
   * @since 0.1.0
   */
  const val USER_STATISTICS: String = "user_statistics"

  /**
   * Represents the entity name for a user wallet.
   *
   * @since 0.1.0
   */
  const val USER_WALLET: String = "user_wallet"
}

/**
 * Type alias for `EntityNames`, providing a shorter, more concise name.
 *
 * @since 0.1.0
 */
typealias En = EntityNames