package me.kvdpxne.dtm.commands

import me.kvdpxne.dtm.command.Command
import me.kvdpxne.dtm.command.CommandBuilder
import me.kvdpxne.dtm.command.ParameterBuilder
import me.kvdpxne.dtm.command.ParameterValidators
import me.kvdpxne.dtm.command.Performer
import me.kvdpxne.dtm.user.UserPerformer

fun createCoinsAddCommand(): Command {
  // Usage: /dtm coins add <VALUE> [USER_NAME]
  return CommandBuilder()
    .name("add")
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
        performer.user.wallet.addCoins(value)
        performer.sendMessage("&6&lDTM &7> &fDo twojego portfela zostało dodane &6$value &fmonet.")
        return@handler
      }

      if (2 == arguments.size) {

        val user = arguments.asFoundUser(1)
        if (null == user) {
          performer.sendMessage("Nie znaleziono użytkownika.")
          return@handler
        }

        val value = arguments.asInt()
        user.wallet.addCoins(value)
        performer.sendMessage("&6&lDTM &7> &fDo portfela użytkownika &6${user.name} &fzostało dodane &6$value &fmonet.")
        return@handler
      }
    }
    .build()
}