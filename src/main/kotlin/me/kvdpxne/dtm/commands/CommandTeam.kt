package me.kvdpxne.dtm.commands

import me.kvdpxne.dtm.command.Command
import me.kvdpxne.dtm.command.CommandBuilder
import me.kvdpxne.dtm.command.Performer

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