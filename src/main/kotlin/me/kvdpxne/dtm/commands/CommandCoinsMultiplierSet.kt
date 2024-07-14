package me.kvdpxne.dtm.commands

import me.kvdpxne.dtm.command.Command
import me.kvdpxne.dtm.command.CommandBuilder
import me.kvdpxne.dtm.command.ParameterBuilder
import me.kvdpxne.dtm.command.ParameterValidators
import me.kvdpxne.dtm.command.Performer
import me.kvdpxne.dtm.command.builderUserNameParameter
import me.kvdpxne.dtm.user.UserPerformer

fun createCoinsMultiplierSetCommand(): Command {
  // Usage: /dtm coins multiplier set <VALUE> [USER_NAME]
  return CommandBuilder()
    .name("set")
    .parameter(
      ParameterBuilder<Float>()
        .name("value")
        .validationBy(ParameterValidators.POSITIVE_FLOAT_VALIDATOR)
        .required()
        .build()
    )
    .parameter(
      builderUserNameParameter()
        .optional()
        .build()
    )
    .handler<Performer> { performer, arguments ->
      if (1 == arguments.size) {

        if (performer !is UserPerformer) {
          performer.sendMessage("")
          return@handler
        }

        val multiplier = arguments.asFloat()
        performer.user.wallet.multiplier = multiplier
        performer.sendMessage("")
        return@handler
      }



    }
    .build()
}