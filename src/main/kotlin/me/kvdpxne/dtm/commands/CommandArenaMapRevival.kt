package me.kvdpxne.dtm.commands

import me.kvdpxne.dtm.command.Command
import me.kvdpxne.dtm.command.CommandBuilder
import me.kvdpxne.dtm.command.Performer

/**
 * @since 0.1.0
 */
fun createArenaMapRevivalCommand(): Command<Performer> {
  /* Usages:
   * /dtm arena map spawn list <ARENA_NAME>
   * /dtm arena map spawn set <ARENA_NAME> <TEAM_NAME>
   */
  return CommandBuilder.begin<Performer>("revival")
    .aliases("spawn", "respawn")
    .hub()
    .children(
      createArenaMapRevivalListCommand(),
      createArenaMapRevivalSetCommand()
    )
    .build()
}