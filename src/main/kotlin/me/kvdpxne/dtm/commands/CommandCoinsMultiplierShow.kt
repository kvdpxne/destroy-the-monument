package me.kvdpxne.dtm.commands

import me.kvdpxne.dtm.command.Command
import me.kvdpxne.dtm.command.CommandBuilder
import me.kvdpxne.dtm.command.Parameters
import me.kvdpxne.dtm.command.Performer
import me.kvdpxne.dtm.translation.formatter.Formatter
import me.kvdpxne.dtm.translation.message.EnumMessageKey
import me.kvdpxne.dtm.user.User

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
        val user: User = attemptObtainUser(performer, parameters)

        performer.prepareMessage(EnumMessageKey.COMMAND_COINS_MULTIPLIER_SHOW_OTHERS)
          .format(
            Formatter.begin(2)
              .with("USER_NAME", user.name)
              .with("VALUE", user.wallet.multiplier)
          )
          .useChat()
          .send()

        return@handler
      }

      //
      val user: User = attemptObtainUserAsSelf(performer)

      performer.prepareMessage(EnumMessageKey.COMMAND_COINS_MULTIPLIER_SHOW_SELF)
        .format(
          Formatter.begin(1)
            .with("VALUE", user.wallet.multiplier)
        )
        .useChat()
        .send()
    }
    .build()
}