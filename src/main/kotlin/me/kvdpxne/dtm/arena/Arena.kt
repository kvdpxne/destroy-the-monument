package me.kvdpxne.dtm.arena

import java.util.UUID
import me.kvdpxne.dtm.position.MonumentPosition
import me.kvdpxne.dtm.position.RevivalPosition
import me.kvdpxne.dtm.shared.ancillary.Identifiable
import me.kvdpxne.dtm.position.BlockPosition
import me.kvdpxne.dtm.team.Team

/**
 * @since 0.1.0
 */
interface Arena : Identifiable<UUID> {

  /**
   * @since 0.1.0
   */
  val name: String

  /**
   * @since 0.1.0
   */
  val revivalPositions: List<RevivalPosition<out Team>>

  /**
   * @since 0.1.0
   */
  val monumentPositions: List<MonumentPosition<out Team>>

  /**
   * @since 0.1.0
   */
  val map: ArenaMap?

  /**
   * @since 0.1.0
   */
  fun getRevivalPosition(
    team: Team
  ): RevivalPosition<out Team>?

  /**
   * @since 0.1.0
   */
  fun getMonumentPositions(
    team: Team
  ): List<MonumentPosition<out Team>>

  /**
   * @since 0.1.0
   */
  fun getMonumentPosition(
    x: Int,
    y: Int,
    z: Int
  ): MonumentPosition<Team>?

  /**
   * @since 0.1.0
   */
  fun getMonumentPosition(
    position: BlockPosition
  ): MonumentPosition<Team>? {
    return this.getMonumentPosition(
      position.x,
      position.y,
      position.z
    )
  }
}