package me.kvdpxne.dtm.data.mapping

import java.util.UUID
import me.kvdpxne.dtm.data.raw.RawTeam
import me.kvdpxne.dtm.data.shared.toPair
import me.kvdpxne.dtm.data.tables.TeamTable
import me.kvdpxne.dtm.data.validation.ValidationResult
import me.kvdpxne.dtm.data.validation.main.validateTeam
import org.jetbrains.exposed.sql.ResultRow

/**
 * Converts a [ResultRow] from the database into a [RawTeam] object.
 *
 * This function validates the team's name and color fields (armor, profession,
 * chat, and player list) to ensure they meet the required criteria. If any
 * field is invalid, an exception is thrown.
 *
 * @param identifier The unique identifier (UUID) of the team.
 * @return A [RawTeam] object representing the team data.
 * @throws NullPointerException If any required field is `null`.
 * @throws IllegalStateException If any field fails validation.
 * @since 0.1.0
 */
internal fun ResultRow.toRawTeam(
  identifier: UUID = this[TeamTable.identifier]
): Pair<RawTeam, ValidationResult> {
  val name: String = this[TeamTable.name]
  val colorOfArmor: String = this[TeamTable.colorOfArmor]
  val colorOfProfession: String = this[TeamTable.colorOfProfession]
  val colorOnChat: String = this[TeamTable.colorOnChat]
  val colorOnPlayerList: String? = this[TeamTable.colorOnPlayerList]

  return RawTeam(
    identifier,
    name,
    colorOfArmor,
    colorOfProfession,
    colorOnChat,
    colorOnPlayerList
  ).toPair(
    validateTeam(
      identifier,
      name,
      colorOfArmor,
      colorOfProfession,
      colorOnChat,
      colorOnPlayerList
    )
  )
}