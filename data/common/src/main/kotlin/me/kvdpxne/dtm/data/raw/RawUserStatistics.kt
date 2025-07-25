package me.kvdpxne.dtm.data.raw

import java.util.UUID
import me.kvdpxne.dtm.data.shared.Raw
import me.kvdpxne.dtm.shared.StylishToStringBuilder
import me.kvdpxne.dtm.util.StylishToStringProvider

/**
 * @property identifier
 * @property kills
 * @property assists
 * @property deaths
 * @property destroyedMonuments
 * @property playedGames
 * @property gamesWon
 * @property gamesLost
 *
 * @author Łukasz Pietrzak (kvdpxne)
 * @since 0.1.0
 */
data class RawUserStatistics(
  val identifier: UUID,
  val kills: Int,
  val assists: Int,
  val deaths: Int,
  val destroyedMonuments: Int,
  val playedGames: Int,
  val gamesWon: Int,
  val gamesLost: Int
) : Raw, StylishToStringProvider {

  /**
   * @since 0.1.0
   */
  override fun toStylishString(): StylishToStringBuilder {
    return StylishToStringBuilder
      .begin("RawUserStatistics")
      .add("identifier", this.identifier)
      .add("kills", this.kills)
      .add("assists", this.assists)
      .add("deaths", this.deaths)
      .add("destroyedMonuments", this.destroyedMonuments)
      .add("playedGames", this.playedGames)
      .add("gamesWon", this.gamesWon)
      .add("gamesLost", this.gamesLost)
  }

  /**
   * @since 0.1.0
   */
  override fun toString(): String {
    return this.toStylishString().packed()
  }
}