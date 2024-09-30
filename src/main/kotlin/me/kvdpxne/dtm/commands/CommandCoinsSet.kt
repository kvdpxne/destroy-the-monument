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
fun createCoinsSetCommand(): Command<Performer> {
  // Usage: /dtm coins set <VALUE> [USER_NAME]
  return CommandBuilder.begin<Performer>("set")
    .parameter(
      ParameterBuilder.begin<Long>("VALUE")
        .validatorHandler(ParameterValidators.POSITIVE_LONG_VALIDATOR)
        .required()
        .build()
    )
    .parameter(
      Parameters.userNameParameter()
        .required()
        .build()
    )
    .handler { performer, parameters ->
      val value: Long = parameters[0] as Long

      if (1 == parameters.size) {

        if (performer !is LocalUserPerformer) {
          throw CommandException("Komenda nie może zostać użyta w konsoli.")
        }

        val oldValue = performer.user.wallet.coins

        performer.user.wallet.coins = value
        performer.sendMessage("&6&lDTM &7> &fZmieniono wartość portfela z &6$oldValue &fna &6$value.")
        return@handler
      }

      if (2 == parameters.size) {
        val userName: String = parameters[1] as String
        val user: User = UserService.findUserByName(userName)
          ?: throw CommandException("Nie znaleziono użytkownika.")

        val oldValue = user.wallet.coins

        user.wallet.coins = value
        performer.sendMessage("&6&lDTM &7> &fZmieniono wartość portfela z &6$oldValue &fna &6$value &fu użytkownika &6${user.name}&f.")
      }
    }
    .build()
}