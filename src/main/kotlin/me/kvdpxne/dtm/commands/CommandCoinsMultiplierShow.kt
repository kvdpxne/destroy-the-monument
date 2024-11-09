package me.kvdpxne.dtm.commands

import me.kvdpxne.dtm.command.Command
import me.kvdpxne.dtm.command.CommandBuilder
import me.kvdpxne.dtm.command.CommandException
import me.kvdpxne.dtm.command.Parameters
import me.kvdpxne.dtm.command.Performer
import me.kvdpxne.dtm.translation.TranslationService
import me.kvdpxne.dtm.translation.formatter.Formatter
import me.kvdpxne.dtm.translation.message.EnumMessageKey
import me.kvdpxne.dtm.user.LocalUserPerformer
import me.kvdpxne.dtm.user.User
import me.kvdpxne.dtm.user.UserService

/**
 * @since 0.1.0
 */
fun createCoinsMultiplierShowCommand(): Command<Performer> {
  // Usage: /dtm coins multiplier show [USER_NAME]
  return CommandBuilder.begin<Performer>("show")
    .parameter(
      Parameters.userNameParameter()
        .optional()
        .build()
    )
    .handler { performer, parameters ->
      if (1 == parameters.size) {
        //
        val userName: String = parameters[0] as String

        //
        val user: User = UserService.findUserByName(userName)
          ?: throw CommandException("Nie znaleziono użytkownika.")

        TranslationService.chains()
          .receiver(performer)
          .message(EnumMessageKey.COMMAND_COINS_MULTIPLIER_SHOW_OTHERS)
          .format(
            Formatter.begin(2)
              .with("USER_NAME", user.name)
              .with("VALUE", user.wallet.multiplier)
          )
          .useChat()
          .send()

        return@handler
      }

      if (performer !is LocalUserPerformer) {
        performer.sendMessage("Komenda nie może zostać użyta w konsoli.")
        return@handler
      }

      TranslationService.chains()
        .receiver(performer)
        .message(EnumMessageKey.COMMAND_COINS_MULTIPLIER_SHOW_SELF)
        .format(
          Formatter.begin(1)
            .with("VALUE", performer.user.wallet.multiplier)
        )
        .useChat()
        .send()
    }
    .build()
}