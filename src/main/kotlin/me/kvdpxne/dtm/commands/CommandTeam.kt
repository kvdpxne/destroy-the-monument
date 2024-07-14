package me.kvdpxne.dtm.commands

import me.kvdpxne.dtm.command.Command
import me.kvdpxne.dtm.command.CommandBuilder

fun createTeamCommand(): Command {
  /* Usages:
   * /dtm team add <GAME_NAME> <TEAM_NAME>
   * /dtm team create <TEAM_NAME>
   * /dtm team list
   * /dtm team remove <TEAM_NAME>
   */
  return CommandBuilder()
    .name("team")
    .hub()
    .children(
      createTeamAddCommand(),
      createTeamCreateCommand(),
      createTeamListCommand(),
      createTeamRemoveCommand()
    )
    .build()
}