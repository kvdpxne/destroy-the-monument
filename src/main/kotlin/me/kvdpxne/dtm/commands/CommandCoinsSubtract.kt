package me.kvdpxne.dtm.commands

import me.kvdpxne.dtm.command.Command
import me.kvdpxne.dtm.command.CommandBuilder
import me.kvdpxne.dtm.command.ParameterBuilder
import me.kvdpxne.dtm.command.ParameterValidators
import me.kvdpxne.dtm.command.Parameters
import me.kvdpxne.dtm.command.Performer
import me.kvdpxne.dtm.translation.formatter.Formatter
import me.kvdpxne.dtm.translation.message.EnumMessageKey
import me.kvdpxne.dtm.user.User

/**
 * @since 0.1.0
 */
fun createCoinsSubtractCommand(): Command<Performer> {
  // Usage: /dtm coins subtract <VALUE> [USER_NAME]
  return CommandBuilder.begin<Performer>("subtract")
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
        //
        val user: User = attemptObtainUserAsSelf(performer)

        //
        user.wallet.subtractCoins(value)

        performer.prepareMessage(EnumMessageKey.COMMAND_COINS_SUBTRACT_SELF)
          .format(
            Formatter.begin(1)
              .with("VALUE", value)
          )
          .useChat()
          .send()

        return@handler
      }

      if (2 == parameters.size) {
        //
        val user: User = attemptObtainUser(performer, parameters, 1)

        user.wallet.subtractCoins(value)

        performer.prepareMessage(EnumMessageKey.COMMAND_COINS_SUBTRACT_OTHERS)
          .format(
            Formatter.begin(2)
              .with("USER_NAME", user.name)
              .with("VALUE", value)
          )
          .useChat()
          .send()

        return@handler
      }
    }
    .build()
}