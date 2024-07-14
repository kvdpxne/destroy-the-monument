package me.kvdpxne.dtm.commands

import me.kvdpxne.dtm.command.Command
import me.kvdpxne.dtm.command.CommandBuilder

fun createTeamCommand(): Command {
  return CommandBuilder()
    .name("team")
    .hub()
    .children(
      createTeamAddCommand(),
      createTeamCreateCommand()
    )
    .build()
}