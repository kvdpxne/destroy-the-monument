package me.kvdpxne.dtm.commands

import me.kvdpxne.dtm.DestroyTheMonument
import me.kvdpxne.dtm.command.Command
import me.kvdpxne.dtm.command.CommandBuilder
import me.kvdpxne.dtm.command.ParameterBuilder
import me.kvdpxne.dtm.command.ParameterValidators
import me.kvdpxne.dtm.configuration.GeneralConfiguration
import me.kvdpxne.dtm.game.LocalGame
import me.kvdpxne.dtm.translation.message.EnumTranslationKey
import me.kvdpxne.dtm.user.performer.LocalUserPerformer

/**
 * @since 0.1.0
 */
fun createGlobalChatCommand(): Command<LocalUserPerformer> {
  // Usage: /g <MESSAGE>
  return CommandBuilder.begin<LocalUserPerformer>("globalChat")
    .aliases("global", "g")
    .parameter(
      ParameterBuilder.begin<String>("MESSAGE")
        .validatorHandler(ParameterValidators.STRING_VALIDATOR)
        .required()
        .varargs()
        .build()
    )
    .handler { performer, parameters ->
      //
      val localGame: LocalGame = performer.user.game
        ?: performer.throwMessage(EnumTranslationKey.MUST_IN_GAME) {
          this@throwMessage.withoutFormat()
        }

      val name: String = performer.name
      val message: String = (parameters[0] as Array<*>).joinToString(" ") {
        it as String
      }

      localGame.sendMessage("&7[&6G&7] &6$name&7: &f$message")

      if (GeneralConfiguration.TRACE_GLOBAL_MESSAGES_IN_GAME) {
        DestroyTheMonument.instance.logger.info(
          "[${localGame.name}] [G] $name: $message"
        )
      }
    }
    .build()
}