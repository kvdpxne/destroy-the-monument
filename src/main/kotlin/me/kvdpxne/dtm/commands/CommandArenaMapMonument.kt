package me.kvdpxne.dtm.commands

import me.kvdpxne.dtm.command.Command
import me.kvdpxne.dtm.command.CommandBuilder
import me.kvdpxne.dtm.command.Performer

/**
 * @since 0.1.0
 */
fun createArenaMapMonumentCommand(): Command<Performer> {
  /* Usages:
   * /dtm arena map monument add <ARENA_NAME> <TEAM_NAME>
   * /dtm arena map monument list <ARENA_NAME>
   * /dtm arena map monument remove <ARENA_NAME> <TEAM_NAME>
   */
  return CommandBuilder.begin<Performer>("monument")
    .hub()
    .children(
      createArenaMapMonumentAddCommand(),
      createArenaMapMonumentListCommand(),
      createArenaMapMonumentRemoveCommand()
    )
    .build()
}