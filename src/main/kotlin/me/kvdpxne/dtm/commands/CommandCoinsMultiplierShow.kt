package me.kvdpxne.dtm.commands

import me.kvdpxne.dtm.command.Command
import me.kvdpxne.dtm.command.CommandBuilder
import me.kvdpxne.dtm.command.Performer
import me.kvdpxne.dtm.command.builderUserNameParameter
import me.kvdpxne.dtm.user.UserPerformer

fun createCoinsMultiplierShowCommand(): Command {
  // Usage: /dtm coins multiplier show [USER_NAME]
  return CommandBuilder()
    .name("show")
    .parameter(
      builderUserNameParameter()
        .optional()
        .build()
    )
    .handler<Performer> { performer, arguments ->
      if (1 == arguments.size) {
        val user = arguments.asFoundUser(1)
        if (null == user) {
          performer.sendMessage("Nie znaleziono użytkownika.")
          return@handler
        }

        val multiplier = user.wallet.multiplier
        performer.sendMessage("&6&lDTM &7> &fMnożnik: &6$multiplier")
        return@handler
      }

      if (performer !is UserPerformer) {
        performer.sendMessage("Komenda nie może zostać użyta w konsoli.")
        return@handler
      }

      val multiplier = performer.user.wallet.multiplier
      performer.sendMessage("&6&lDTM &7> &fMnożnik: &6$multiplier")
    }
    .build()
}