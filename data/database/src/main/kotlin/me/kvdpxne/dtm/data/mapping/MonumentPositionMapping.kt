package me.kvdpxne.dtm.data.mapping

import java.util.UUID
import me.kvdpxne.dtm.data.raw.RawMonumentPosition
import me.kvdpxne.dtm.data.raw.RawTeam
import me.kvdpxne.dtm.data.shared.toPair
import me.kvdpxne.dtm.data.tables.MonumentPositionTable
import me.kvdpxne.dtm.data.tables.RevivalPositionTable
import me.kvdpxne.dtm.data.validation.ValidationResult
import me.kvdpxne.dtm.data.validation.main.validateMonumentPosition
import org.jetbrains.exposed.sql.ResultRow

/**
 * @param identifier
 *
 * @throws IllegalArgumentException
 * @throws IllegalStateException
 *
 * @since 0.1.0
 */
internal fun ResultRow.toRawMonumentPosition(
  identifier: UUID = this[MonumentPositionTable.identifier],
  team: Pair<RawTeam?, ValidationResult> =
    this[RevivalPositionTable.teamIdentifier].let { teamIdentifier: UUID ->
      this.toRawTeam(teamIdentifier)
    }
): Pair<RawMonumentPosition, ValidationResult> {
  val notNullTeam: RawTeam = checkNotNull(team.first) {
    "Team ${team.first} does not exist."
  }

  val x: Int = this[MonumentPositionTable.x]
  val z: Int = this[MonumentPositionTable.z]
  val y: Int = this[MonumentPositionTable.y]

  return RawMonumentPosition(
    identifier,
    notNullTeam,
    x,
    z,
    y
  ).toPair(
    validateMonumentPosition(
      identifier,
      null,
      x,
      z,
      y
    ).combine(team.second)
  )
}

fun mapToRawMonumentPositions(
  rows: List<ResultRow>
): List<Pair<RawMonumentPosition, ValidationResult>> {
  return rows
    .map(ResultRow::toRawMonumentPosition)
    .distinct()
    .toList()
}