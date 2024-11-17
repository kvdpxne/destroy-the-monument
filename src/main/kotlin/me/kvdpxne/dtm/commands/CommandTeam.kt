package me.kvdpxne.dtm.commands

import me.kvdpxne.dtm.command.Command
import me.kvdpxne.dtm.command.CommandBuilder
import me.kvdpxne.dtm.command.Performer
import me.kvdpxne.dtm.team.Team
import me.kvdpxne.dtm.team.TeamService
import me.kvdpxne.dtm.translation.formatter.Formatter
import me.kvdpxne.dtm.translation.message.EnumMessageKey

/**
 * @since 0.1.0
 */
fun createTeamCommand(): Command<Performer> {
  /* Usages:
   * /dtm team add <GAME_NAME> <TEAM_NAME>
   * /dtm team create <TEAM_NAME>
   * /dtm team list
   * /dtm team remove <TEAM_NAME>
   */
  return CommandBuilder.begin<Performer>("team")
    .hub()
    .children(
      createTeamAddCommand(),
      createTeamCreateCommand(),
      createTeamListCommand(),
      createTeamRemoveCommand()
    )
    .build()
}

internal fun attemptObtainTeam(
  receiver: Performer,
  parameters: Array<Any>,
  index: Int = 0
): Team {
  // A unique team name registered in local memory or stored in the database.
  val teamName: String = parameters[index] as String

  //
  return TeamService.findTeamByName(teamName)
    ?: receiver.throwMessage(EnumMessageKey.TEAM_NO_FOUND) {
      this@throwMessage.format(
        Formatter.begin(1)
          .with("TEAM_NAME", teamName)
      )
    }
}