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

fun createCoinsSubtractCommand(): Command<Performer> {
  // Usage: /dtm coins subtract <VALUE> [USER_NAME]
  return CommandBuilder.begin<Performer>("subtract")
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
      val value: Long = parameters[0] as Long

      if (1 == parameters.size) {

        if (performer !is LocalUserPerformer) {
          throw CommandException("Komenda nie może zostać użyta w konsoli.")
        }

        performer.user.wallet.subtractCoins(value)
        performer.sendMessage("&6&lDTM &7> &fZ twojego portfela zostało odjęte &6$value &fmonet.")
        return@handler
      }

      if (2 == parameters.size) {

        val userName: String = parameters[0] as String
        val user: User = UserService.findUserByName(userName)
          ?: throw CommandException("Nie znaleziono użytkownika.")

        user.wallet.subtractCoins(value)
        performer.sendMessage("&6&lDTM &7> &fZ portfela użytkownika &6${user.name} &fzostało odjęte &6$value &fmonet.")
        return@handler
      }
    }
    .build()
}