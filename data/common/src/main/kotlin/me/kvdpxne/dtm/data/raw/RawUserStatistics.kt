package me.kvdpxne.dtm.data.raw

import java.util.UUID
import org.jetbrains.annotations.VisibleForTesting

/**
 * @param identifier
 * @param kills
 * @param assists
 * @param deaths
 * @param destroyedMonuments
 * @param playedGames
 * @param gamesWon
 * @param gamesLost
 *
 * @since 0.1.0
 */
data class RawUserStatistics @VisibleForTesting constructor(
  // @formatter:off
  val identifier        : UUID,
  val kills             : Int,
  val assists           : Int,
  val deaths            : Int,
  val destroyedMonuments: Int,
  val playedGames       : Int,
  val gamesWon          : Int,
  val gamesLost         : Int
  // @formatter:on
)