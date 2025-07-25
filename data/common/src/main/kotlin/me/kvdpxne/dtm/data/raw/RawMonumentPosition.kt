package me.kvdpxne.dtm.data.raw

import java.util.UUID
import me.kvdpxne.dtm.data.shared.Raw
import me.kvdpxne.dtm.shared.StylishToStringBuilder
import me.kvdpxne.dtm.util.StylishToString
import me.kvdpxne.dtm.util.StylishToStringProvider

/**
 * @property identifier
 * @property team
 * @property x
 * @property z
 * @property y
 *
 * @author Łukasz Pietrzak (kvdpxne)
 * @since 0.1.0
 */
data class RawMonumentPosition(
  val identifier: UUID,
  val team: RawTeam,
  val x: Int,
  val z: Int,
  val y: Int
) : Raw, StylishToStringProvider {

  /**
   * @since 0.1.0
   */
  override fun toStylishString(): StylishToString {
    return StylishToStringBuilder
      .begin("RawMonumentPosition")
      .add("identifier", this.identifier)
      .addNested("team", this.team)
      .add("x", this.x)
      .add("z", this.z)
      .add("y", this.y)
  }

  /**
   * @since 0.1.0
   */
  override fun toString(): String {
    return this.toStylishString().packed()
  }
}
