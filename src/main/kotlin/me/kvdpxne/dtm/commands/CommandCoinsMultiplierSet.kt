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

fun createCoinsMultiplierSetCommand(): Command<Performer> {
  // Usage: /dtm coins multiplier set <VALUE> [USER_NAME]
  return CommandBuilder.begin<Performer>("set")
    .parameter(
      ParameterBuilder.begin<Float>("value")
        .validatorHandler(ParameterValidators.POSITIVE_FLOAT_VALIDATOR)
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
      val value: Float = parameters[0] as Float

      if (1 == parameters.size) {

        if (performer !is LocalUserPerformer) {
          throw CommandException("Komenda nie może zostać użyta w konsoli.")
        }

        val oldMultiplier = performer.user.wallet.multiplier

        performer.user.wallet.updateMultiplier(value)
        performer.sendMessage("&6&lDTM &7> &fZmieniono mnożnik z &6$oldMultiplier &fna &6$value.")
        return@handler
      }

      if (2 == parameters.size) {
        val userName: String = parameters[1] as String
        val user: User = UserService.findUserByName(userName)
          ?: throw CommandException("Nie znaleziono użytkownika.")

        val oldMultiplier = user.wallet.multiplier

        user.wallet.updateMultiplier(value)
        performer.sendMessage("&6&lDTM &7> &fZmieniono mnożnik z &6$oldMultiplier &fna &6$value.")
        return@handler
      }
    }
    .build()
}