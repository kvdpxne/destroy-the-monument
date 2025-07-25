package me.kvdpxne.dtm.data.mapping

import java.util.UUID
import me.kvdpxne.dtm.data.raw.RawArenaMap
import me.kvdpxne.dtm.data.shared.toPair
import me.kvdpxne.dtm.data.tables.ArenaMapTable
import me.kvdpxne.dtm.data.validation.ValidationResult
import me.kvdpxne.dtm.data.validation.main.validateArenaMap
import org.jetbrains.exposed.sql.ResultRow

/**
 * @since 0.1.0
 */
internal fun ResultRow.toRawArenaMap(
  identifier: UUID = this[ArenaMapTable.identifier]
): Pair<RawArenaMap, ValidationResult> {
  val name: String = this[ArenaMapTable.name]

  return RawArenaMap(
    identifier,
    name
  ).toPair(
    validateArenaMap(
      identifier,
      name
    )
  )
}