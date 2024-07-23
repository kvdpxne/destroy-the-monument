package me.kvdpxne.dtm.commands

import me.kvdpxne.dtm.command.Command
import me.kvdpxne.dtm.command.CommandBuilder

fun createArenaMapMonumentCommand(): Command {
  /* Usages:
   * /dtm arena map monument add <ARENA_NAME> <TEAM_NAME>
   * /dtm arena map monument list <ARENA_NAME>
   * /dtm arena map monument remove <ARENA_NAME> <TEAM_NAME>
   */
  return CommandBuilder()
    .name("monument")
    .hub()
    .children(
      createArenaMapMonumentAddCommand(),
      createArenaMapMonumentListCommand(),
      createArenaMapMonumentRemoveCommand()
    )
    .build()
}