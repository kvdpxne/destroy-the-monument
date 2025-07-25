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
 * @property pitch
 * @property yaw
 *
 * @author Łukasz Pietrzak (kvdpxne)
 * @since 0.1.0
 */
data class RawRevivalPosition(
  val identifier: UUID,
  val team: RawTeam,
  val x: Double,
  val z: Double,
  val y: Double,
  val pitch: Float,
  val yaw: Float
) : Raw, StylishToStringProvider {

  /**
   * @since 0.1.0
   */
  override fun toStylishString(): StylishToString {
    return StylishToStringBuilder
      .begin("RawRevivalPosition")
      .add("identifier", this.identifier)
      .addNested("team", this.team)
      .add("x", this.x)
      .add("z", this.z)
      .add("y", this.y)
      .add("pitch", this.pitch)
      .add("yaw", this.yaw)
  }

  /**
   * @since 0.1.0
   */
  override fun toString(): String {
    return this.toStylishString().packed()
  }
}
