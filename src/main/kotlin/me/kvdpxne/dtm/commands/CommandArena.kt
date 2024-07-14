package me.kvdpxne.dtm.commands

import me.kvdpxne.dtm.command.Command
import me.kvdpxne.dtm.command.CommandBuilder

fun createArenaCommand(): Command {
  /* Usages:
   * /dtm arena add <ARENA_NAME> <GAME_NAME>
   * /dtm arena create <ARENA_NAME>
   * /dtm arena list
   * /dtm arena remove <ARENA_NAME>
   * /dtm arena map
   **/
  return CommandBuilder()
    .name("arena")
    .hub()
    .children(
      createArenaAddCommand(),
      createArenaCreateCommand(),
      createArenaListCommand(),
      createArenaRemoveCommand(),

      createArenaMapCommand(),
    )
    .build()
}