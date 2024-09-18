package me.kvdpxne.dtm.commands

import me.kvdpxne.dtm.command.Command
import me.kvdpxne.dtm.command.CommandBuilder
import me.kvdpxne.dtm.command.Performer

fun createArenaCommand(): Command<Performer> {
  /* Usages:
   * /dtm arena add <ARENA_NAME> <GAME_NAME>
   * /dtm arena create <ARENA_NAME>
   * /dtm arena list
   * /dtm arena remove <ARENA_NAME>
   * /dtm arena map
   **/
  return CommandBuilder.begin<Performer>("arena")
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