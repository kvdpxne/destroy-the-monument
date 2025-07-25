package me.kvdpxne.dtm.data.mapping

import java.util.UUID
import me.kvdpxne.dtm.data.raw.RawArena
import me.kvdpxne.dtm.data.raw.RawArenaMap
import me.kvdpxne.dtm.data.raw.RawMonumentPosition
import me.kvdpxne.dtm.data.raw.RawRevivalPosition
import me.kvdpxne.dtm.data.shared.firsts
import me.kvdpxne.dtm.data.shared.seconds
import me.kvdpxne.dtm.data.shared.toPair
import me.kvdpxne.dtm.data.tables.ArenaMapTable
import me.kvdpxne.dtm.data.tables.ArenaTable
import me.kvdpxne.dtm.data.validation.BasicValidationResult
import me.kvdpxne.dtm.data.validation.ValidationResult
import me.kvdpxne.dtm.data.validation.common.isNameValid
import me.kvdpxne.dtm.data.validation.main.validateArena
import org.jetbrains.exposed.sql.Query
import org.jetbrains.exposed.sql.ResultRow

/**
 * @since 0.1.0
 */
internal fun mapToRawArena(
  query: Query
): Pair<RawArena, ValidationResult>? {
  val rows: List<ResultRow> = query.toList()
  if (rows.isEmpty()) {
    return null
  }

  val first: ResultRow = rows.first()

  val map: Pair<RawArenaMap, ValidationResult> = first.toRawArenaMap()

  val monumentPositions: List<Pair<RawMonumentPosition, ValidationResult>> =
    mapToRawMonumentPositions(rows)

  val revivalPositions: List<Pair<RawRevivalPosition, ValidationResult>> =
    mapToRawRevivalPositions(rows)

  val identifier: UUID = first[ArenaTable.identifier]
  val name: String = first[ArenaTable.name]

  return RawArena(
    identifier,
    map.first,
    monumentPositions.firsts(),
    revivalPositions.firsts(),
    name
  ).toPair(
    validateArena(
      identifier,
      null,
      null,
      null,
      name
    ).combine(
      map.second,
      *monumentPositions.seconds(),
      *revivalPositions.seconds()
    )
  )
}

/**
 * @since 0.1.0
 */
internal fun mapToRawArena(
  row: ResultRow,
  identifier: UUID = row[ArenaTable.identifier],
  arenaMap: Pair<RawArenaMap?, ValidationResult> =
    row.toRawArenaMap(row[ArenaMapTable.identifier])
): Pair<RawArena, ValidationResult> {
  val name: String = row[ArenaTable.name]

  return RawArena(
    identifier,
    arenaMap.first,
    emptyList(),
    emptyList(),
    name
  ).toPair(
    validateArena(
      identifier,
      null,
      null,
      null,
      name
    ).combine(arenaMap.second)
  )
}