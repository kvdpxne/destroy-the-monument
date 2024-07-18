package me.kvdpxne.dtm.commands

import me.kvdpxne.dtm.command.Command
import me.kvdpxne.dtm.command.CommandBuilder
import me.kvdpxne.dtm.command.Performer
import me.kvdpxne.dtm.command.builderUserNameParameter
import me.kvdpxne.dtm.user.UserPerformer

fun createCoinsShowCommand(): Command {
  // Usage: /dtm coins show [USER_NAME]
  return CommandBuilder()
    .name("show")
    .parameter(
      builderUserNameParameter()
        .optional()
        .build()
    )
    .handler<Performer> { performer, arguments ->
      if (arguments.isEmpty()) {

        if (performer !is UserPerformer) {
          performer.sendMessage("Komenda nie może zostać użyta w konsoli.")
          return@handler
        }

        performer.sendMessage("&6&lDTM &7> &fMonety: &6${performer.user.wallet.coins}")
        return@handler
      }

      if (1 == arguments.size) {
        val user = arguments.asFoundUser(1)
        if (null == user) {
          performer.sendMessage("Nie znaleziono użytkownika.")
          return@handler
        }

        val name = user.name
        val coins = user.wallet.coins

        performer.sendMessage("&6&lDTM &7> &fMonety użytkownika &6$name&f: &6$coins")
        return@handler
      }
    }
    .build()
}