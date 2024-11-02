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
fun createCoinsAddCommand(): Command<Performer> {
  // Usage: /dtm coins add <VALUE> [USER_NAME]
  return CommandBuilder.begin<Performer>("add")
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
      //
      val value: Long = parameters[0] as Long

      if (1 == parameters.size) {

        if (performer !is LocalUserPerformer) {
          performer.sendMessage("Komenda nie może zostać użyta w konsoli.")
          return@handler
        }

        performer.user.wallet.addCoins(value)

        TranslationService.chains()
          .receiver(performer)
          .message(MessageKeys.COMMAND_COINS_ADD_SELF)
          .formatter(
            Formatter.begin(1)
              .with("VALUE", value)
          )
          .send()

        return@handler
      }

      val userName: String = parameters[1] as String

      val user: User = UserService.findUserByName(userName)
        ?: throw CommandException("Nie znaleziono użytkownika.")

      user.wallet.addCoins(value)

      TranslationService.chains()
        .receiver(performer)
        .message(MessageKeys.COMMAND_COINS_ADD_OTHERS)
        .formatter(
          Formatter.begin(2)
            .with("USER_NAME", user.name)
            .with("VALUE", value)
        )
        .send()
    }
    .build()
}