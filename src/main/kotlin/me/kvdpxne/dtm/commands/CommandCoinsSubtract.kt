package me.kvdpxne.dtm.commands

import me.kvdpxne.dtm.command.Command
import me.kvdpxne.dtm.command.CommandBuilder
import me.kvdpxne.dtm.command.ParameterBuilder
import me.kvdpxne.dtm.command.ParameterValidators
import me.kvdpxne.dtm.command.Performer

fun createCoinsSubtractCommand(): Command {
  // Usage: /dtm coins subtract <VALUE> [USER_NAME]
  return CommandBuilder()
    .name("subtract")
    .parameter(
      ParameterBuilder<Int>()
        .name("value")
        .validationBy(ParameterValidators.POSITIVE_INTEGER_VALIDATOR)
        .required()
        .build()
    )
    .handler<Performer> { performer, arguments ->

    }
    .build()
}