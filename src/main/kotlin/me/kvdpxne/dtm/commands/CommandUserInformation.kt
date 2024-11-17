package me.kvdpxne.dtm.commands

import me.kvdpxne.dtm.command.Command
import me.kvdpxne.dtm.command.CommandBuilder
import me.kvdpxne.dtm.command.Parameters
import me.kvdpxne.dtm.command.Performer
import me.kvdpxne.dtm.user.User

fun createUserInformationCommand(): Command<Performer> {
  return CommandBuilder.begin<Performer>("information")
    .aliases("info", "i")
    .parameter(
      Parameters.userNameParameter()
        .optional()
        .build()
    )
    .handler { performer, parameters ->
      if (1 == parameters.size) {
        //
        val user: User = attemptObtainUser(performer, parameters)

        return@handler
      }

      //
      val user: User = attemptObtainUserAsSelf(performer)
    }
    .build()
}