package me.kvdpxne.dtm.commands

import me.kvdpxne.dtm.command.Command
import me.kvdpxne.dtm.command.CommandBuilder

fun createArenaMapCommand(): Command {
  return CommandBuilder()
    .name("map")
    .hub()
    .children(
      createArenaMapSetCommand()
    )
    .build()
}