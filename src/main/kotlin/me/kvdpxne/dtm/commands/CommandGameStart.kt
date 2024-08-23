package me.kvdpxne.dtm.commands

import me.kvdpxne.dtm.command.Command
import me.kvdpxne.dtm.command.CommandBuilder
import me.kvdpxne.dtm.command.Performer
import me.kvdpxne.dtm.command.builderGameNameParameter
import me.kvdpxne.dtm.game.Game
import me.kvdpxne.dtm.game.GameManager
import me.kvdpxne.dtm.game.LocalGame
import me.kvdpxne.dtm.game.LocalTeam
import me.kvdpxne.dtm.user.UserPerformer

object CommandGameStart {

  private fun startGame(game: LocalGame?, user: Performer) {
    if (null == game) {
      user.sendMessage("No found game.")
      user.sendMessage("Usage: /dtm start <GAME_NAME>")
      return
    }

    game.start()

    user.sendMessage("The game ${game.name} has started.")
  }

  fun createStartCommand(): Command {
    // Usage: /dtm start [GAME_NAME]
    return CommandBuilder()
      .name("start")
      .parameter(
        builderGameNameParameter()
          .optional()
          .build()
      )
      .handler<Performer> { performer, arguments ->
        if (arguments.isEmpty()) {

          if (performer !is UserPerformer) {
            performer.sendMessage("Command is not accessible from the console.")
            return@handler
          }

          val game = performer.user.game
          startGame(game as LocalGame, performer)
          return@handler
        }

        val game = arguments.asFoundGame<LocalTeam, LocalGame>()
        startGame(game, performer)
      }
      .build()
  }
}