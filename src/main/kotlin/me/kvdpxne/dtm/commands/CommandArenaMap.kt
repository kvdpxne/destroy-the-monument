package me.kvdpxne.dtm.commands

import me.kvdpxne.dtm.command.Command
import me.kvdpxne.dtm.command.CommandBuilder

fun createArenaMapCommand(): Command {
  /* Usages:
   * /dtm arena map list
   * /dtm arena map monument
   * /dtm arena map revival
   * /dtm arena map set <ARENA_NAME> <MAP_NAME>
   */
  return CommandBuilder()
    .name("map")
    .hub()
    .children(
      createArenaMapListCommand(),
      createArenaMapMonumentCommand(),
      createArenaMapRevivalCommand(),
      createArenaMapSetCommand()
    )
    .build()
}