package me.kvdpxne.dtm.data.mapping

import java.util.UUID
import me.kvdpxne.dtm.data.raw.RawRevivalPosition
import me.kvdpxne.dtm.data.raw.RawTeam
import me.kvdpxne.dtm.data.shared.toPair
import me.kvdpxne.dtm.data.tables.RevivalPositionTable
import me.kvdpxne.dtm.data.validation.ValidationResult
import me.kvdpxne.dtm.data.validation.main.validateRevivalPosition
import org.jetbrains.exposed.sql.ResultRow

/**
 * @since 0.1.0
 */
internal fun ResultRow.toRawRevivalPosition(
  identifier: UUID = this[RevivalPositionTable.identifier],
  team: Pair<RawTeam?, ValidationResult> =
    this[RevivalPositionTable.teamIdentifier].let { teamIdentifier: UUID ->
      this.toRawTeam(teamIdentifier)
    }
): Pair<RawRevivalPosition, ValidationResult> {
  val notNullTeam: RawTeam = checkNotNull(team.first) {
    "Team ${team.first} does not exist."
  }

  val x: Double = this[RevivalPositionTable.x]
  val z: Double = this[RevivalPositionTable.z]
  val y: Double = this[RevivalPositionTable.y]
  val pitch: Float = this[RevivalPositionTable.pitch]
  val yaw: Float = this[RevivalPositionTable.yaw]

  return RawRevivalPosition(
    identifier,
    notNullTeam,
    x,
    z,
    y,
    pitch,
    yaw
  ).toPair(
    validateRevivalPosition(
      identifier,
      null,
      x,
      z,
      y,
      pitch,
      yaw
    ).combine(team.second)
  )
}

fun mapToRawRevivalPositions(
  rows: Collection<ResultRow>
): List<Pair<RawRevivalPosition, ValidationResult>> {
  return rows
    .map(ResultRow::toRawRevivalPosition)
    .distinct()
    .toList()
}