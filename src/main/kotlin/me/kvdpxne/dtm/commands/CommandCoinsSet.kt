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

        //
        val user: User = attemptObtainUserAsSelf(performer)

        val oldValue: Long = user.wallet.coins
        user.wallet.coins = value

        performer.prepareMessage(EnumMessageKey.COMMAND_COINS_SET_SELF)
          .format(
            Formatter.begin(2)
              .with("OLD_VALUE", oldValue)
              .with("NEW_VALUE", value)
          )
          .useChat()
          .send()

        return@handler
      }

      if (2 == parameters.size) {
        //
        val user: User = attemptObtainUser(performer, parameters, 1)

        val oldValue: Long = user.wallet.coins
        user.wallet.coins = value

        performer.prepareMessage(EnumMessageKey.COMMAND_COINS_SET_OTHERS)
          .format(
            Formatter.begin(3)
              .with("OLD_VALUE", oldValue)
              .with("NEW_VALUE", value)
              .with("USER_NAME", user.name)
          )
          .useChat()
          .send()

        return@handler
      }
    }
    .build()
}