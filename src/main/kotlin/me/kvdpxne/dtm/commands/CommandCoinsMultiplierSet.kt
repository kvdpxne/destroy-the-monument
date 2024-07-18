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
          performer.sendMessage("Komenda nie może zostać użyta w konsoli.")
          return@handler
        }

        val oldMultiplier = performer.user.wallet.multiplier
        val newMultiplier = arguments.asFloat()

        performer.user.wallet.multiplier = newMultiplier
        performer.sendMessage("&6&lDTM &7> &fZmieniono mnożnik z &6$oldMultiplier &fna &6$newMultiplier.")
        return@handler
      }

      if (2 == arguments.size) {
        val user = arguments.asFoundUser(1)
        if (null == user) {
          performer.sendMessage("Nie znaleziono użytkownika.")
          return@handler
        }

        val oldMultiplier = user.wallet.multiplier
        val newMultiplier = arguments.asFloat()

        user.wallet.multiplier = newMultiplier
        performer.sendMessage("&6&lDTM &7> &fZmieniono mnożnik z &6$oldMultiplier &fna &6$newMultiplier.")
        return@handler
      }
    }
    .build()
}