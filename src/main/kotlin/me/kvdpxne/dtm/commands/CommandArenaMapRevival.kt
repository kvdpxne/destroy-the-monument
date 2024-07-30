package me.kvdpxne.dtm.commands

import me.kvdpxne.dtm.command.Command
import me.kvdpxne.dtm.command.CommandBuilder

fun createArenaMapRevivalCommand(): Command {
  /* Usages:
   * /dtm arena map spawn list <ARENA_NAME>
   * /dtm arena map spawn set <ARENA_NAME> <TEAM_NAME>
   */
  return CommandBuilder()
    .name("revival")
    .aliases("spawn", "respawn")
    .hub()
    .children(
      createArenaMapRevivalListCommand(),
      createArenaMapRevivalSetCommand()
    )
    .build()
}