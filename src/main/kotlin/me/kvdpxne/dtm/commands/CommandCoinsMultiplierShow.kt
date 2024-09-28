package me.kvdpxne.dtm.commands

import me.kvdpxne.dtm.command.Command
import me.kvdpxne.dtm.command.CommandBuilder
import me.kvdpxne.dtm.command.CommandException
import me.kvdpxne.dtm.command.Parameters
import me.kvdpxne.dtm.command.Performer
import me.kvdpxne.dtm.user.LocalUserPerformer
import me.kvdpxne.dtm.user.User
import me.kvdpxne.dtm.user.UserService

/**
 * @since 0.1.0
 */
fun createCoinsMultiplierShowCommand(): Command<Performer> {
  // Usage: /dtm coins multiplier show [USER_NAME]
  return CommandBuilder.begin<Performer>("show")
    .parameter(
      Parameters.userNameParameter()
        .optional()
        .build()
    )
    .handler { performer, parameters ->
      if (1 == parameters.size) {
        //
        val userName: String = parameters[0] as String

        //
        val user: User = UserService.findUserByName(userName)
          ?: throw CommandException("Nie znaleziono użytkownika.")

        val multiplier = user.wallet.multiplier
        performer.sendMessage("&6&lDTM &7> &fMnożnik: &6$multiplier")
        return@handler
      }

      if (performer !is LocalUserPerformer) {
        performer.sendMessage("Komenda nie może zostać użyta w konsoli.")
        return@handler
      }

      val multiplier = performer.user.wallet.multiplier
      performer.sendMessage("&6&lDTM &7> &fMnożnik: &6$multiplier")
    }
    .build()
}