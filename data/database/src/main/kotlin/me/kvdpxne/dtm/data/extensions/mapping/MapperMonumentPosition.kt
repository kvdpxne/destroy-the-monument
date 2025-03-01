package me.kvdpxne.dtm.data.extensions.mapping

import java.util.UUID
import me.kvdpxne.dtm.data.raw.RawMonumentPosition
import me.kvdpxne.dtm.data.raw.RawTeam
import me.kvdpxne.dtm.data.tables.TableMonumentPosition
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
  identifier: UUID
): RawMonumentPosition {
  val team: RawTeam = this.toTeam(
    this[TableMonumentPosition.teamIdentifier]
  )

  val x: Int = this[TableMonumentPosition.x]
  val y: Int = this[TableMonumentPosition.y]
  val z: Int = this[TableMonumentPosition.z]

  return RawMonumentPosition(
    identifier,
    team,
    x,
    y,
    z
  )
}

/**
 * @since 0.1.0
 */
internal fun ResultRow.toRawMonumentPosition(): RawMonumentPosition {
  return this.toRawMonumentPosition(
    this[TableMonumentPosition.identifier]
  )
}