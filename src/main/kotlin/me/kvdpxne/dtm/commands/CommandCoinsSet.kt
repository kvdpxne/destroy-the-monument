package me.kvdpxne.dtm.commands

import me.kvdpxne.dtm.command.Command
import me.kvdpxne.dtm.command.CommandBuilder
import me.kvdpxne.dtm.command.ParameterBuilder
import me.kvdpxne.dtm.command.ParameterValidators
import me.kvdpxne.dtm.command.Performer
import me.kvdpxne.dtm.command.builderUserNameParameter
import me.kvdpxne.dtm.user.UserPerformer

fun createCoinsSetCommand(): Command {
  // Usage: /dtm coins set <VALUE> [USER_NAME]
  return CommandBuilder()
    .name("set")
    .parameter(
      ParameterBuilder<Int>()
        .name("value")
        .validationBy(ParameterValidators.POSITIVE_INTEGER_VALIDATOR)
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
          performer.sendMessage("Command is not accessible from the console.")
          return@handler
        }

        val value = arguments.asInt()
        performer.user.wallet.coins = value
        performer.sendMessage("&fAdded &6$value &fcoins to your wallet.")
        return@handler
      }

      val value = arguments.asInt()
    }
    .build()
}