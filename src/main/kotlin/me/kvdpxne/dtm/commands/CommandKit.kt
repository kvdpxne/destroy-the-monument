package me.kvdpxne.dtm.commands

import me.kvdpxne.dtm.command.Command
import me.kvdpxne.dtm.command.CommandBuilder
import me.kvdpxne.dtm.gui.createProfessionSelectionGui
import me.kvdpxne.dtm.user.UserPerformer

fun createKitCommand(): Command {
  return CommandBuilder()
    .name("kit")
    .aliases("")
    .handler<UserPerformer> { performer, _ ->
      performer.getPlayer()?.let {
        createProfessionSelectionGui(performer.user).open(it)
      }
    }
    .build()
}