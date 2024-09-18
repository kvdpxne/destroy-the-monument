package me.kvdpxne.dtm.commands

import me.kvdpxne.dtm.command.Command
import me.kvdpxne.dtm.command.CommandBuilder
import me.kvdpxne.dtm.command.Performer

fun createGameCommand(): Command<Performer> {
  /* Usages:
   * /dtm game create <GAME_NAME>
   * /dtm game list
   * /dtm game remove <GAME_NAME>
   * /dtm game start [GAME_NAME]
   * /dtm game stop [GAME_NAME]
   */
  return CommandBuilder.begin<Performer>("game")
    .hub()
    .children(
      createGameCreateCommand(),
      createGameListCommand(),
      createGameRemoveCommand(),
      CommandGameStart.createStartCommand(),
      CommandGameStop.createStopCommand(),
    )
    .build()
}