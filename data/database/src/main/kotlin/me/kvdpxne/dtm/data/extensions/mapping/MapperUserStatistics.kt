package me.kvdpxne.dtm.data.extensions.mapping

import java.util.UUID
import me.kvdpxne.dtm.data.raw.RawUserStatistics
import me.kvdpxne.dtm.data.tables.TableUserStatistics
import me.kvdpxne.dtm.data.validation.context.isUserStatisticsFieldValid
import org.jetbrains.exposed.sql.ResultRow

/**
 * @param identifier
 *
 * @throws IllegalArgumentException
 * @throws IllegalStateException
 *
 * @since 0.1.0
 */
internal fun ResultRow.toRawUserStatistics(
  identifier: UUID,
): RawUserStatistics {
  val kills: Int = this[TableUserStatistics.kills]
  check(isUserStatisticsFieldValid(kills)) {
    "The database returned an invalid number of kills ($kills)."
  }

  val assists = this[TableUserStatistics.assists]
  check(isUserStatisticsFieldValid(assists)) {
    "The database returned an invalid number of assists ($assists)."
  }

  val deaths: Int = this[TableUserStatistics.deaths]
  check(isUserStatisticsFieldValid(deaths)) {
    "The database returned an invalid number of deaths ($deaths)."
  }

  val destroyedMonuments: Int = this[TableUserStatistics.destroyedMonuments]
  check(isUserStatisticsFieldValid(destroyedMonuments)) {
    "The database returned an invalid number of destroyed monuments ($destroyedMonuments)."
  }

  val playedGames = this[TableUserStatistics.playedGames]
  check(isUserStatisticsFieldValid(playedGames)) {
    "The database returned an invalid number of played games ($playedGames)."
  }

  val gamesWon: Int = this[TableUserStatistics.gamesWon]
  check(isUserStatisticsFieldValid(gamesWon)) {
    "The database returned an invalid number of games ($gamesWon)."
  }

  val gamesLost: Int = this[TableUserStatistics.gamesLost]
  check(isUserStatisticsFieldValid(gamesLost)) {
    "The database returned an invalid number of games ($gamesLost)."
  }

  return RawUserStatistics(
    identifier,
    kills,
    assists,
    deaths,
    destroyedMonuments,
    playedGames,
    gamesWon,
    gamesLost
  )
}

/**
 * @since 0.1.0
 */
internal fun ResultRow.toRawUserStatistics(): RawUserStatistics {
  return this.toRawUserStatistics(
    this[TableUserStatistics.identifier]
  )
}
