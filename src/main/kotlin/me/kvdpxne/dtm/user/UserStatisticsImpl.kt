package me.kvdpxne.dtm.user

import java.util.UUID
import me.kvdpxne.dtm.statistics.IdentifiableStatisticsImpl

/**
 * A class representing statistics for a user in the game.
 *
 * This class inherits from `BaseIdentifiableStatistics` and provides additional
 * statistics specific to user performance such as played games, games won, and
 * games lost. It also defines methods for incrementing each of these values.
 *
 * @param kills The initial number of kills (default 0).
 * @param assists The initial number of assists (default 0).
 * @param deaths The initial number of deaths (default 0).
 * @param destroyedMonuments The initial number of destroyed monuments (default 0).
 * @param playedGames The initial number of played games (default 0).
 * @param gamesWon The initial number of games won (default 0).
 * @param gamesLost The initial number of games lost (default 0).
 * @param identifier
 *
 * @since 0.1.0
 */
class UserStatisticsImpl(
  // @formatter:off
               kills             : Int  = 0,
               assists           : Int  = 0,
               deaths            : Int  = 0,
               destroyedMonuments: Int  = 0,
  override var playedGames       : Int  = 0,
  override var gamesWon          : Int  = 0,
  override var gamesLost         : Int  = 0,
               identifier        : UUID = UUID.randomUUID()
  // @formatter:on
) : IdentifiableStatisticsImpl(kills, assists, deaths, destroyedMonuments,
  identifier), UserStatistics {

  override fun addPlayedGames(playedGames: Int) {
    this.playedGames = this.add(this.playedGames, playedGames)
  }

  override fun addGamesWon(gamesWon: Int) {
    this.gamesWon = this.add(this.gamesWon, gamesWon)
  }

  override fun addGamesLost(gamesLost: Int) {
    this.gamesLost = this.add(this.gamesLost, gamesLost)
  }

  override fun reset() {
    super.reset()

    this.playedGames = 0
    this.gamesWon = 0
    this.gamesLost = 0
  }
}