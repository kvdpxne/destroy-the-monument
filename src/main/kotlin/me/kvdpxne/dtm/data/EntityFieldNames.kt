package me.kvdpxne.dtm.data

object EntityFieldNames {

  const val IDENTIFIER = "identifier"
  const val NAME = "name"

  // User
  const val LOCALE = "locale"
  const val PROFESSION_NAME = "profession"

  // User
  const val STATISTICS_IDENTIFIER = "statistics_${this.IDENTIFIER}"
  const val WALLET_IDENTIFIER = "wallet_${this.IDENTIFIER}"
  const val MONUMENT_POSITION_IDENTIFIER = "monument_position_${this.IDENTIFIER}"
  const val REVIVAL_POSITION_IDENTIFIER = "revival_position_${this.IDENTIFIER}"
  const val TEAM_IDENTIFIER = "team_${this.IDENTIFIER}"
  const val GAME_IDENTIFIER = "game_${this.IDENTIFIER}"
  const val ARENA_IDENTIFIER = "arena_${this.IDENTIFIER}"

  // User statistics
  const val KILLS = "kills"
  const val ASSISTS = "assists"
  const val DEATHS = "deaths"
  const val DESTROYED_MONUMENTS = "destroyed_monuments"
  const val PLAYED_GAMES = "played_games"
  const val GAMES_WON = "games_won"
  const val GAMES_LOST = "games_lost"

  // User wallet
  const val COINS = "coins"
  const val MULTIPLIER = "multiplier"

  // Positions
  const val X = "x"
  const val Y = "y"
  const val Z = "z"
  const val PITCH = "pitch"
  const val YAW = "yaw"
}