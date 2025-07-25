package me.kvdpxne.dtm.raw.factories

import java.util.UUID
import me.kvdpxne.dtm.data.raw.RawUserStatistics
import me.kvdpxne.dtm.shared.randomPositiveInt
import me.kvdpxne.dtm.shared.uniqueUuid

/**
 * Factory function that generates a [RawUserStatistics] instance with configurable parameters
 * and safe defaults. All numerical statistics default to random positive integers.
 *
 * @param previousIdentifier Optional UUID to avoid when generating a new unique identifier.
 * @param identifier Unique identifier for the statistics record (default: new UUID unique relative to `previousIdentifier`).
 * @param kills Number of kills (default: random positive integer).
 * @param assists Number of assists (default: random positive integer).
 * @param deaths Number of deaths (default: random positive integer).
 * @param destroyedMonuments Number of monuments destroyed (default: random positive integer).
 * @param playedGames Total games played (default: random positive integer).
 * @param gamesWon Games won (default: random positive integer).
 * @param gamesLost Games lost (default: random positive integer).
 * @return Configured [RawUserStatistics] instance.
 *
 * @since 0.1.0
 * @see uniqueUuid
 * @see randomPositiveInt
 */
fun makeRawUserStatistics(
  // @formatter:off
  previousIdentifier: UUID? = null,
  identifier        : UUID  = uniqueUuid(previousIdentifier),
  kills             : Int   = randomPositiveInt(),
  assists           : Int   = randomPositiveInt(),
  deaths            : Int   = randomPositiveInt(),
  destroyedMonuments: Int   = randomPositiveInt(),
  playedGames       : Int   = randomPositiveInt(),
  gamesWon          : Int   = randomPositiveInt(),
  gamesLost         : Int   = randomPositiveInt()
  // @formatter:on
) = RawUserStatistics(
  identifier,
  kills,
  assists,
  deaths,
  destroyedMonuments,
  playedGames,
  gamesWon,
  gamesLost
)