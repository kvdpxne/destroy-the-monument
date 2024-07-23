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
          performer.sendMessage("Komenda nie może zostać użyta w konsoli.")
          return@handler
        }

        val oldValue = performer.user.wallet.coins
        val newValue = arguments.asInt()

        performer.user.wallet.coins = newValue
        performer.sendMessage("&6&lDTM &7> &fZmieniono wartość portfela z &6$oldValue &fna &6$newValue.")
        return@handler
      }

      if (2 == arguments.size) {
        val user = arguments.asFoundUser(1)
        if (null == user) {
          performer.sendMessage("Nie znaleziono użytkownika.")
          return@handler
        }

        val oldValue = user.wallet.coins
        val newValue = arguments.asInt()

        user.wallet.coins = newValue
        performer.sendMessage("&6&lDTM &7> &fZmieniono wartość portfela z &6$oldValue &fna &6$newValue &fu użytkownika &6${user.name}&f.")
      }
    }
    .build()
}