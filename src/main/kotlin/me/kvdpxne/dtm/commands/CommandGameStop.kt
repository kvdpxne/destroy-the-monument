package me.kvdpxne.dtm.commands

import me.kvdpxne.dtm.command.Command
import me.kvdpxne.dtm.command.CommandBuilder
import me.kvdpxne.dtm.command.CommandException
import me.kvdpxne.dtm.command.Parameters
import me.kvdpxne.dtm.command.Performer
import me.kvdpxne.dtm.game.GameManager
import me.kvdpxne.dtm.game.LocalGame
import me.kvdpxne.dtm.team.LocalTeam
import me.kvdpxne.dtm.user.LocalUserPerformer

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

  fun createStopCommand(): Command<Performer> {
    // Usage: /dtm stop [GAME_NAME]
    return CommandBuilder.begin<Performer>("stop")
      .parameter(
        Parameters.localGameNameParameter()
          .optional()
          .build()
      )
      .handler { performer, parameters ->
        if (parameters.isEmpty()) {
          //
          if (performer !is LocalUserPerformer) {
            throw CommandException("Command is not accessible from the console.")
          }

          val game = performer.user.game
          stopGame(game, performer)
          return@handler
        }

        //
        val gameName: String = parameters[0] as String

        //
        val game: LocalGame = GameManager.findGameByName<LocalTeam, LocalGame>(gameName)
          ?: throw CommandException("")

        stopGame(game, performer)
      }
      .build()
  }
}