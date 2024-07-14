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
          performer.sendMessage("Command is not accessible from the console.")
          return@handler
        }

        performer.sendMessage("&fCoins: &6${performer.user.wallet.coins}")
        return@handler
      }


    }
    .build()
}