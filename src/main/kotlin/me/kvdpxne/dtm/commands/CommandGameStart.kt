package me.kvdpxne.dtm.commands

import me.kvdpxne.dtm.command.Command
import me.kvdpxne.dtm.command.CommandBuilder
import me.kvdpxne.dtm.command.CommandException
import me.kvdpxne.dtm.command.Parameters
import me.kvdpxne.dtm.command.Performer
import me.kvdpxne.dtm.game.GameManager
import me.kvdpxne.dtm.game.LocalGame
import me.kvdpxne.dtm.translation.formatter.Formatter
import me.kvdpxne.dtm.translation.message.EnumTranslationKey
import me.kvdpxne.dtm.user.performer.LocalUserPerformer

object CommandGameStart {

  private fun startGame(game: LocalGame?, user: Performer) {
    if (null == game) {
      user.sendMessage("No found game.")
      user.sendMessage("Usage: /dtm start <GAME_NAME>")
      return
    }

    game.start()

    user.prepareMessage(EnumTranslationKey.COMMAND_START)
      .format(
        Formatter.begin(1)
          .with("GAME_NAME", game.name)
      )
      .useChat()
      .send()
  }

  fun createStartCommand(): Command<Performer> {
    // Usage: /dtm start [GAME_NAME]
    return CommandBuilder.begin<Performer>("start")
      .aliases("s", "run", "r")
      .parameter(
        Parameters.localGameNameParameter()
          .optional()
          .build()
      )
      .handler { performer, parameters ->
        if (parameters.isEmpty()) {
          //
          if (performer !is LocalUserPerformer) {
            performer.throwMessage(EnumTranslationKey.COMMAND_IN_GAME) {
              this@throwMessage.withoutFormat()
            }
          }

          val game = performer.user.game
          startGame(game, performer)
          return@handler
        }

        //
        val gameName: String = parameters[0] as String

        //
        val game: LocalGame = GameManager.findGameByNameOrNull(gameName)
          ?: throw CommandException("")

        startGame(game, performer)
      }
      .build()
  }
}