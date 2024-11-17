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
fun createCoinsMultiplierSetCommand(): Command<Performer> {
  // Usage: /dtm coins multiplier set <VALUE> [USER_NAME]
  return CommandBuilder.begin<Performer>("set")
    .parameter(
      ParameterBuilder.begin<Float>("value")
        .validatorHandler(ParameterValidators.POSITIVE_FLOAT_VALIDATOR)
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
      val value: Float = parameters[0] as Float

      if (1 == parameters.size) {
        //
        val user: User = attemptObtainUserAsSelf(performer)

        val oldMultiplier = user.wallet.multiplier
        user.wallet.multiplier = value

        performer.prepareMessage(EnumMessageKey.COMMAND_COINS_MULTIPLIER_SET_SELF)
          .format(
            Formatter.begin(2)
              .with("OLD_VALUE", oldMultiplier)
              .with("NEW_VALUE", value)
          )
          .useChat()
          .send()

        return@handler
      }

      if (2 == parameters.size) {
        //
        val user: User = attemptObtainUser(performer, parameters, 1)

        val oldMultiplier = user.wallet.multiplier
        user.wallet.multiplier = value

        performer.prepareMessage(EnumMessageKey.COMMAND_COINS_MULTIPLIER_SET_OTHERS)
          .format(
            Formatter.begin(3)
              .with("USER_NAME", user.name)
              .with("OLD_VALUE", oldMultiplier)
              .with("NEW_VALUE", value)
          )
          .useChat()
          .send()

        return@handler
      }
    }
    .build()
}