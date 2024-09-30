package me.kvdpxne.dtm.commands

import me.kvdpxne.dtm.command.Command
import me.kvdpxne.dtm.command.CommandBuilder
import me.kvdpxne.dtm.command.CommandException
import me.kvdpxne.dtm.command.ParameterBuilder
import me.kvdpxne.dtm.command.ParameterValidators
import me.kvdpxne.dtm.game.GameManager
import me.kvdpxne.dtm.game.LocalGame
import me.kvdpxne.dtm.user.LocalUserPerformer

/**
 * @since 0.1.0
 */
fun createGlobalChatCommand(): Command<LocalUserPerformer> {
  // Usage: /g <TEXT...>
  return CommandBuilder.begin<LocalUserPerformer>("globalChat")
    .aliases("global", "g")
    .parameter(
      ParameterBuilder.begin<String>("TEXT")
        .validatorHandler(ParameterValidators.STRING_VALIDATOR)
        .required()
        .varargs()
        .build()
    )
    .handler { performer, parameters ->
      //
      val localGame: LocalGame = performer.user.game
        ?: throw CommandException("&cBłąd&8: Nie jesteś w grze.")

      val name: String = performer.name
      val textLine: String = parameters.joinToString(" ")

      localGame.sendMessage("&7[&6G&7] &6$name&7: &f$textLine")
    }
    .build()
}