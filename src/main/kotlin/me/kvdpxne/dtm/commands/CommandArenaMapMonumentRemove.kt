package me.kvdpxne.dtm.commands

import me.kvdpxne.dtm.command.Command
import me.kvdpxne.dtm.command.CommandBuilder
import me.kvdpxne.dtm.user.LocalUserPerformer

fun createArenaMapMonumentRemoveCommand(): Command<LocalUserPerformer> {
  return CommandBuilder.begin<LocalUserPerformer>("remove")
    .aliases("rm", "delete", "del")
    .handler { performer, parameters ->

    }
    .build()
}