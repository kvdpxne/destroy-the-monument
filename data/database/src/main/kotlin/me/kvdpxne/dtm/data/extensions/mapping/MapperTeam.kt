package me.kvdpxne.dtm.data.extensions.mapping

import java.util.UUID
import me.kvdpxne.dtm.data.raw.RawTeam
import me.kvdpxne.dtm.data.tables.TableTeam
import me.kvdpxne.dtm.data.validation.context.isTeamColorFieldValid
import me.kvdpxne.dtm.data.validation.context.isTeamNameValid
import me.kvdpxne.dtm.shared.toSingleLines
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
internal fun ResultRow.toTeam(
  identifier: UUID
): RawTeam {
  val name: String = this[TableTeam.name]
  check(isTeamNameValid(name)) {
    """
      The database returned an invalid team name "$name";
      Team identifier: "$identifier".
    """.toSingleLines()
  }

  val colorOfArmor: String = this[TableTeam.colorOfArmor]
  check(isTeamColorFieldValid(colorOfArmor)) {
    """
      The database returned an invalid team armor color "$colorOfArmor";
      Team identifier: "$identifier".
    """.toSingleLines()
  }

  val colorOfProfession: String = this[TableTeam.colorOfProfession]
  check(isTeamColorFieldValid(colorOfProfession)) {
    """
      The database returned an invalid team profession color "$colorOfProfession";

      The database returned an invalid team profession color
      "$colorOfProfession" for a team with the identifier "$identifier".
    """.toSingleLines()
  }

  val colorOnChat: String = this[TableTeam.colorOnChat]
  check(isTeamColorFieldValid(colorOnChat)) {
    """
      The database returned an invalid team color "$colorOnChat" used on the
      chat for the team with the identifier "$identifier".
    """.toSingleLines()
  }

  val colorOnPlayerList: String? = this[TableTeam.colorOnPlayerList]
  check(isTeamColorFieldValid(colorOnPlayerList)) {
    """
      The database returned an invalid team color "$colorOnPlayerList" used on
      the player list for the team with the identifier "$identifier".
    """.toSingleLines()
  }

  return RawTeam(
    identifier,
    name,
    colorOfArmor,
    colorOfProfession,
    colorOnChat,
    colorOnPlayerList
  )
}

/**
 * Converts a [ResultRow] from the database into a [RawTeam] object.
 *
 * This function extracts the team's identifier from the [ResultRow] and
 * delegates to the [toTeam] function that takes a UUID parameter.
 *
 * @return A [RawTeam] object representing the team data.
 * @since 0.1.0
 */
internal fun ResultRow.toTeam(): RawTeam {
  return this.toTeam(
    this[TableTeam.identifier]
  )
}