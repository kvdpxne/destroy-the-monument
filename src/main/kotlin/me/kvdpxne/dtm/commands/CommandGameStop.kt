package me.kvdpxne.dtm.commands

import me.kvdpxne.dtm.command.Command
import me.kvdpxne.dtm.command.CommandBuilder
import me.kvdpxne.dtm.command.Performer
import me.kvdpxne.dtm.command.builderGameNameParameter
import me.kvdpxne.dtm.game.GameManager
import me.kvdpxne.dtm.game.LocalGame
import me.kvdpxne.dtm.game.LocalTeam
import me.kvdpxne.dtm.user.UserPerformer

object CommandGameStop {

  private fun stopGame(game: LocalGame?, performer: Performer) {
    if (null == game) {
      performer.sendMessage("No found game.")
      performer.sendMessage("Usage: /dtm stop <GAME_NAME>")
      return
    }

    try {
      game.stop()
    } catch (exception: IllegalArgumentException) {
      // It's stupid but for the current phase the point is that the world in
      // which the player is located will be unloaded. it cannot be discharged
      // while any player is on this world.
      performer.sendMessage("The game cannot be stopped.")
      return
    }

    performer.sendMessage("The ${game.name} game has been stopped.")
  }

  fun createStopCommand(): Command {
    // Usage: /dtm stop [GAME_NAME]
    return CommandBuilder()
      .name("stop")
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
          stopGame(game as LocalGame, performer)
          return@handler
        }

        val game = arguments.asFoundGame<LocalTeam, LocalGame>()
        stopGame(game, performer)
      }
      .build()
  }
}