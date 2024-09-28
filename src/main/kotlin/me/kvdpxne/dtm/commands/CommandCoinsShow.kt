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
fun createCoinsShowCommand(): Command<Performer> {
  // Usage: /dtm coins show [USER_NAME]
  return CommandBuilder.begin<Performer>("show")
    .parameter(
      Parameters.userNameParameter()
        .optional()
        .build()
    )
    .handler { performer, parameters ->
      if (parameters.isEmpty()) {

        if (performer !is LocalUserPerformer) {
          throw CommandException("Komenda nie może zostać użyta w konsoli.")
        }

        performer.sendMessage("&6&lDTM &7> &fMonety: &6${performer.user.wallet.coins}")
        return@handler
      }

      if (1 == parameters.size) {
        val userName: String = parameters[0] as String
        val user: User = UserService.findUserByName(userName)
          ?: throw CommandException("Nie znaleziono użytkownika.")

        val name = user.name
        val coins = user.wallet.coins

        performer.sendMessage("&6&lDTM &7> &fMonety użytkownika &6$name&f: &6$coins")
        return@handler
      }
    }
    .build()
}