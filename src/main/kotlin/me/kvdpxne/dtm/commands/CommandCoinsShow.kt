package me.kvdpxne.dtm.commands

import me.kvdpxne.dtm.command.Command
import me.kvdpxne.dtm.command.CommandBuilder
import me.kvdpxne.dtm.command.Parameters
import me.kvdpxne.dtm.command.Performer
import me.kvdpxne.dtm.translation.formatter.Formatter
import me.kvdpxne.dtm.translation.message.EnumTranslationKey
import me.kvdpxne.dtm.user.User

/**
 * @since 0.1.0
 */
fun createCoinsShowCommand(): Command<Performer> {
  // Usage: /dtm coins show [USER_NAME]
  return CommandBuilder.begin<Performer>("show")
    .parameter(
      Parameters.userNameParameter()
        .optional()
        .build()
    )
    .handler { performer, parameters ->
      if (1 == parameters.size) {
        //
        val user: User = attemptObtainUser(performer, parameters)

        performer.prepareMessage(EnumTranslationKey.COMMAND_COINS_SHOW_OTHERS)
          .format(
            Formatter.begin(1)
              .with("USER_NAME", user.name)
              .with("VALUE", user.wallet.coins)
          )
          .useChat()
          .send()

        return@handler
      }

      //
      val user: User = attemptObtainUserAsSelf(performer)

      performer.prepareMessage(EnumTranslationKey.COMMAND_COINS_SHOW_SELF)
        .format(
          Formatter.begin(1)
            .with("VALUE", user.wallet.coins)
        )
        .useChat()
        .send()
    }
    .build()
}