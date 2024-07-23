package me.kvdpxne.dtm.commands

import me.kvdpxne.dtm.command.Command
import me.kvdpxne.dtm.command.CommandBuilder

fun createArenaMapSpawnCommand(): Command {
  /* Usages:
   * /dtm arena map spawn list <ARENA_NAME>
   * /dtm arena map spawn set <ARENA_NAME> <TEAM_NAME>
   */
  return CommandBuilder()
    .name("spawn")
    .hub()
    .children(
      createArenaMapSpawnListCommand(),
      createArenaMapSpawnSetCommand()
    )
    .build()
}