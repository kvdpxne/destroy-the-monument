package me.kvdpxne.dtm.data.mapping

import me.kvdpxne.dtm.data.raw.RawUserStatistics
import me.kvdpxne.dtm.data.shared.mapNotNullTo
import me.kvdpxne.dtm.user.statistics.UserStatistics

/**
 * Converts [UserStatistics] domain object to its raw data representation [RawUserStatistics].
 *
 * @return [RawUserStatistics] instance with equivalent properties
 * @since 0.1.0
 */
fun UserStatistics.toRawUserStatistics(): RawUserStatistics {
  return RawUserStatistics(
    this.identifier,
    this.kills,
    this.assists,
    this.deaths,
    this.destroyedMonuments,
    this.playedGames,
    this.gamesWon,
    this.gamesLost
  )
}

/**
 * @param initialCapacity
 * @param distinct
 *
 * @since 0.1.0
 */
fun Iterable<UserStatistics?>.toRawUserStatistics(
  initialCapacity: Int = 12,
  distinct: Boolean = true
): List<RawUserStatistics> {
  return mapNotNullTo(
    this,
    initialCapacity,
    distinct,
    UserStatistics::toRawUserStatistics
  )
}