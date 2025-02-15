package me.kvdpxne.dtm.commands

import me.kvdpxne.dtm.command.Command
import me.kvdpxne.dtm.command.CommandBuilder
import me.kvdpxne.dtm.command.Performer
import me.kvdpxne.dtm.game.Game
import me.kvdpxne.dtm.game.GameService
import me.kvdpxne.dtm.team.Team
import me.kvdpxne.dtm.translation.formatter.Formatter
import me.kvdpxne.dtm.translation.message.EnumTranslationKey

/**
 * @since 0.1.0
 */
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

/**
 * @param receiver
 * @param parameters
 * @param index
 *
 * @since 0.1.0
 */
internal fun attemptObtainGame(
  receiver: Performer,
  parameters: Array<Any>,
  index: Int = 0
): Game<Team> {
  // Unikatowa nazwa obiektu gry przechowywanej w bazie danych.
  val gameName: String = parameters[index] as String

  // Obiekt gry znaleziony na podstawie unikatowej nazwy gry.
  return GameService.findGameByName(gameName)
    ?: receiver.throwMessage(EnumTranslationKey.GAME_NO_FOUND) {
      this@throwMessage.format(
        Formatter.begin(1)
          .with("GAME_NAME", gameName)
      )
    }
}