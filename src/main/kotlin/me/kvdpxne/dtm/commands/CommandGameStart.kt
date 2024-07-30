package me.kvdpxne.dtm.commands

import me.kvdpxne.dtm.command.Command
import me.kvdpxne.dtm.command.CommandBuilder
import me.kvdpxne.dtm.command.Performer
import me.kvdpxne.dtm.command.builderGameNameParameter
import me.kvdpxne.dtm.game.temporary.Game
import me.kvdpxne.dtm.game.GameManager
import me.kvdpxne.dtm.user.UserPerformer

object CommandGameStart {

  private fun startGame(game: Game?, user: Performer) {
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

          val game = GameManager.findByUser(performer.user)
          startGame(game, performer)
          return@handler
        }

        val game = arguments.asFoundGame()
        startGame(game, performer)
      }
      .build()
  }
}