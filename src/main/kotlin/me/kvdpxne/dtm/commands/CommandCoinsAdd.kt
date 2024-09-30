package me.kvdpxne.dtm.commands

import me.kvdpxne.dtm.command.Command
import me.kvdpxne.dtm.command.CommandBuilder
import me.kvdpxne.dtm.command.CommandException
import me.kvdpxne.dtm.command.ParameterBuilder
import me.kvdpxne.dtm.command.ParameterValidators
import me.kvdpxne.dtm.command.Parameters
import me.kvdpxne.dtm.command.Performer
import me.kvdpxne.dtm.user.LocalUserPerformer
import me.kvdpxne.dtm.user.User
import me.kvdpxne.dtm.user.UserService

/**
 * @since 0.1.0
 */
fun createCoinsAddCommand(): Command<Performer> {
  // Usage: /dtm coins add <VALUE> [USER_NAME]
  return CommandBuilder.begin<Performer>("add")
    .parameter(
      ParameterBuilder.begin<Long>("VALUE")
        .validatorHandler(ParameterValidators.POSITIVE_LONG_VALIDATOR)
        .required()
        .build()
    )
    .parameter(
      Parameters.userNameParameter()
        .optional()
        .build()
    )
    .handler { performer, parameters ->
      //
      val value: Long = parameters[0] as Long

      if (1 == parameters.size) {

        if (performer !is LocalUserPerformer) {
          performer.sendMessage("Komenda nie może zostać użyta w konsoli.")
          return@handler
        }

        performer.user.wallet.addCoins(value)
        performer.sendMessage("&6&lDTM &7> &fDo twojego portfela zostało dodane &6$value &fmonet.")
        return@handler
      }

      val userName: String = parameters[1] as String

      val user: User = UserService.findUserByName(userName)
        ?: throw CommandException("Nie znaleziono użytkownika.")

      user.wallet.addCoins(value)
      performer.sendMessage("&6&lDTM &7> &fDo portfela użytkownika &6${user.name} &fzostało dodane &6$value &fmonet.")
    }
    .build()
}