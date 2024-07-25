package me.kvdpxne.dtm.commands

import me.kvdpxne.dtm.command.Command
import me.kvdpxne.dtm.command.CommandBuilder

fun createAbilityCommand(): Command {
  return CommandBuilder()
    .name("ability")
    .aliases("skill")
    .hub()
    .children(
      createAbilityRenewCommand()
    )
    .build()
}