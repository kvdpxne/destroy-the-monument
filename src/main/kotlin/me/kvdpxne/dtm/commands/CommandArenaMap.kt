package me.kvdpxne.dtm.commands

import me.kvdpxne.dtm.command.Command
import me.kvdpxne.dtm.command.CommandBuilder
import me.kvdpxne.dtm.command.Performer

/**
 * @since 0.1.0
 */
fun createArenaMapCommand(): Command<Performer> {
  /* Usages:
   * /dtm arena map list
   * /dtm arena map monument
   * /dtm arena map revival
   * /dtm arena map set <ARENA_NAME> <MAP_NAME>
   */
  return CommandBuilder.begin<Performer>("map")
    .hub()
    .children(
      createArenaMapListCommand(),
      createArenaMapMonumentCommand(),
      createArenaMapRevivalCommand(),
      createArenaMapSetCommand()
    )
    .build()
}