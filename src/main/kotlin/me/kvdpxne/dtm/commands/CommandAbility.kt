package me.kvdpxne.dtm.commands

import me.kvdpxne.dtm.command.Command
import me.kvdpxne.dtm.command.CommandBuilder

fun createAbilityCommand(): Command {
  /* Usages:
   * /dtm ability deplete
   * /dtm ability renew
   */
  return CommandBuilder()
    .name("ability")
    .aliases("skill", "capability")
    .hub()
    .children(
      createAbilityDepleteCommand(),
      createAbilityRenewCommand()
    )
    .build()
}