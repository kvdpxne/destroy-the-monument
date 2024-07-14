package me.kvdpxne.dtm.commands

import me.kvdpxne.dtm.command.Command
import me.kvdpxne.dtm.command.CommandBuilder

fun createArenaCommand(): Command {
  /* Usage:
   * /dtm arena add    <ARENA_NAME> <GAME_NAME>
   * /dtm arena create <ARENA_NAME>
   * /dtm arena list
   * /dtm arena map
   **/
  return CommandBuilder()
    .name("arena")
    .hub()
    .children(
      createArenaAddCommand(),
      createArenaCreateCommand(),
      createArenaListCommand()
    )
    .build()
}