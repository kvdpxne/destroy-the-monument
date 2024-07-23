package me.kvdpxne.dtm.commands

import me.kvdpxne.dtm.command.Command
import me.kvdpxne.dtm.command.CommandBuilder
import me.kvdpxne.dtm.command.ParameterBuilder
import me.kvdpxne.dtm.command.ParameterValidators
import me.kvdpxne.dtm.command.Performer
import me.kvdpxne.dtm.user.UserPerformer

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
      if (1 == arguments.size) {

        if (performer !is UserPerformer) {
          performer.sendMessage("Komenda nie może zostać użyta w konsoli.")
          return@handler
        }

        val value = arguments.asInt()
        performer.user.wallet.subtractCoins(value)
        performer.sendMessage("&6&lDTM &7> &fZ twojego portfela zostało odjęte &6$value &fmonet.")
        return@handler
      }

      if (2 == arguments.size) {

        val user = arguments.asFoundUser(1)
        if (null == user) {
          performer.sendMessage("Nie znaleziono użytkownika.")
          return@handler
        }

        val value = arguments.asInt()
        user.wallet.subtractCoins(value)
        performer.sendMessage("&6&lDTM &7> &fZ portfela użytkownika &6${user.name} &fzostało odjęte &6$value &fmonet.")
        return@handler
      }
    }
    .build()
}