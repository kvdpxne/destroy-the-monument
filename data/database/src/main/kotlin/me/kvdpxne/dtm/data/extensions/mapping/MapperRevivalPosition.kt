package me.kvdpxne.dtm.data.extensions.mapping

import java.util.UUID
import me.kvdpxne.dtm.data.raw.RawRevivalPosition
import me.kvdpxne.dtm.data.raw.RawTeam
import me.kvdpxne.dtm.data.tables.TableRevivalPosition
import me.kvdpxne.dtm.data.validation.context.isRevivalPositionPitchValid
import me.kvdpxne.dtm.data.validation.context.isRevivalPositionYawValid
import me.kvdpxne.dtm.shared.toSingleLines
import org.jetbrains.exposed.sql.ResultRow

/**
 * @param identifier
 *
 * @throws IllegalArgumentException
 * @throws IllegalStateException
 *
 * @since 0.1.0
 */
internal fun ResultRow.toRawRevivalPosition(
  identifier: UUID
): RawRevivalPosition {
  val team: RawTeam = this.toTeam(
    this[TableRevivalPosition.teamIdentifier]
  )

  val x: Double = this[TableRevivalPosition.x]
  val y: Double = this[TableRevivalPosition.y]
  val z: Double = this[TableRevivalPosition.z]

  val pitch: Float = this[TableRevivalPosition.pitch]
  check(isRevivalPositionPitchValid(pitch)) {
    """
      The database returned an invalid pitch of the revival position "$pitch"
      for the revival position with the identifier "$identifier".
    """.toSingleLines()
  }

  val yaw: Float = this[TableRevivalPosition.yaw]
  check(isRevivalPositionYawValid(yaw)) {
    """
      The database returned an invalid yaw of the revival position "$yaw"
      for the revival position with the identifier "$identifier".
    """.toSingleLines()
  }

  return RawRevivalPosition(
    identifier,
    team,
    x,
    y,
    z,
    pitch,
    yaw
  )
}

/**
 * @since 0.1.0
 */
internal fun ResultRow.toRawRevivalPosition(): RawRevivalPosition {
  return this.toRawRevivalPosition(
    this[TableRevivalPosition.identifier]
  )
}