package me.kvdpxne.dtm.data.mapping

import java.util.UUID
import me.kvdpxne.dtm.data.raw.RawUserStatistics
import me.kvdpxne.dtm.data.shared.toPair
import me.kvdpxne.dtm.data.tables.UserStatisticsTable
import me.kvdpxne.dtm.data.validation.ValidationResult
import me.kvdpxne.dtm.data.validation.main.validateUserStatistics
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
  identifier: UUID = this[UserStatisticsTable.identifier]
): Pair<RawUserStatistics, ValidationResult> {
  val kills: Int = this[UserStatisticsTable.kills]
  val assists = this[UserStatisticsTable.assists]
  val deaths: Int = this[UserStatisticsTable.deaths]
  val destroyedMonuments: Int = this[UserStatisticsTable.destroyedMonuments]
  val playedGames = this[UserStatisticsTable.playedGames]
  val gamesWon: Int = this[UserStatisticsTable.gamesWon]
  val gamesLost: Int = this[UserStatisticsTable.gamesLost]

  return RawUserStatistics(
    identifier,
    kills,
    assists,
    deaths,
    destroyedMonuments,
    playedGames,
    gamesWon,
    gamesLost
  ).toPair(
    validateUserStatistics(
      identifier,
      kills,
      assists,
      deaths,
      destroyedMonuments,
      playedGames,
      gamesWon,
      gamesLost
    )
  )
}
