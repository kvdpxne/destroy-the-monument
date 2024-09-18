package me.kvdpxne.dtm.commands

import me.kvdpxne.dtm.command.Command
import me.kvdpxne.dtm.command.CommandBuilder
import me.kvdpxne.dtm.command.Performer

fun createAbilityCommand(): Command<Performer> {
  /* Usages:
   * /dtm ability deplete
   * /dtm ability renew
   */
  return CommandBuilder.begin<Performer>("ability")
    .aliases("skill", "capability")
    .hub()
    .children(
      createAbilityDepleteCommand(),
      createAbilityRenewCommand()
    )
    .build()
}