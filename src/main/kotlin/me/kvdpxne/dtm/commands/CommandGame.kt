package me.kvdpxne.dtm.commands

import me.kvdpxne.dtm.command.Command
import me.kvdpxne.dtm.command.CommandBuilder

fun createGameCommand(): Command {
  /* Usages:
   * /dtm game create <GAME_NAME>
   * /dtm game list
   * /dtm game remove <GAME_NAME>
   * /dtm game start [GAME_NAME]
   * /dtm game stop [GAME_NAME]
   */
  return CommandBuilder()
    .name("game")
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