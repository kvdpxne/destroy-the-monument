package me.kvdpxne.dtm.commands

import me.kvdpxne.dtm.command.Command
import me.kvdpxne.dtm.command.CommandBuilder
import me.kvdpxne.dtm.command.Parameters
import me.kvdpxne.dtm.command.Performer
import me.kvdpxne.dtm.game.Game
import me.kvdpxne.dtm.game.GameImpl
import me.kvdpxne.dtm.game.GameService
import me.kvdpxne.dtm.team.Team
import me.kvdpxne.dtm.translation.TranslationService
import me.kvdpxne.dtm.translation.formatter.Formatter
import me.kvdpxne.dtm.translation.message.EnumMessageKey

/**
 * @since 0.1.0
 */
fun createGameCreateCommand(): Command<Performer> {
  // Usage: /dtm game create <GAME_NAME>
  return CommandBuilder.begin<Performer>("create")
    .parameter(
      Parameters.gameNameParameter()
        .required()
        .build()
    )
    .handler { performer, parameters ->
      //
      val gameName: String = parameters[0] as String

      //
      val game: Game<Team> = GameImpl(gameName, gameName)

      GameService.insertGame(game)

      TranslationService.chains()
        .receiver(performer)
        .message(EnumMessageKey.COMMAND_GAME_CREATE)
        .format(
          Formatter.begin(1)
            .with("GAME_NAME", game.name)
        )
        .useChat()
        .send()
    }
    .build()
}