package me.kvdpxne.dtm.commands

import me.kvdpxne.dtm.command.Command
import me.kvdpxne.dtm.command.CommandBuilder
import me.kvdpxne.dtm.command.CommandException
import me.kvdpxne.dtm.command.ParameterBuilder
import me.kvdpxne.dtm.command.ParameterValidators
import me.kvdpxne.dtm.command.Parameters
import me.kvdpxne.dtm.command.Performer
import me.kvdpxne.dtm.translation.TranslationService
import me.kvdpxne.dtm.translation.formatter.Formatter
import me.kvdpxne.dtm.translation.message.MessageKeys
import me.kvdpxne.dtm.user.LocalUserPerformer
import me.kvdpxne.dtm.user.User
import me.kvdpxne.dtm.user.UserService

/**
 * @since 0.1.0
 */
fun createCoinsSetCommand(): Command<Performer> {
  // Usage: /dtm coins set <VALUE> [USER_NAME]
  return CommandBuilder.begin<Performer>("set")
    .parameter(
      ParameterBuilder.begin<Long>("VALUE")
        .validatorHandler(ParameterValidators.POSITIVE_LONG_VALIDATOR)
        .required()
        .build()
    )
    .parameter(
      Parameters.userNameParameter()
        .optional()
        .build()
    )
    .handler { performer, parameters ->
      val value: Long = parameters[0] as Long

      if (1 == parameters.size) {

        if (performer !is LocalUserPerformer) {
          throw CommandException("Komenda nie może zostać użyta w konsoli.")
        }

        val oldValue: Long = performer.user.wallet.coins
        performer.user.wallet.coins = value

        TranslationService.chains()
          .receiver(performer)
          .message(MessageKeys.COMMAND_COINS_SET_SELF)
          .formatter(
            Formatter.begin(2)
              .with("OLD_VALUE", oldValue)
              .with("NEW_VALUE", value)
          )
          .send()

        return@handler
      }

      if (2 == parameters.size) {
        val userName: String = parameters[1] as String
        val user: User = UserService.findUserByName(userName)
          ?: throw CommandException("Nie znaleziono użytkownika.")

        val oldValue: Long = user.wallet.coins
        user.wallet.coins = value

        TranslationService.chains()
          .receiver(performer)
          .message(MessageKeys.COMMAND_COINS_SET_OTHERS)
          .formatter(
            Formatter.begin(3)
              .with("OLD_VALUE", oldValue)
              .with("NEW_VALUE", value)
              .with("USER_NAME", user.name)
          )
          .send()

        return@handler
      }
    }
    .build()
}