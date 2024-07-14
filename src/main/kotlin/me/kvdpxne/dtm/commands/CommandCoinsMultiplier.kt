package me.kvdpxne.dtm.commands

import me.kvdpxne.dtm.command.Command
import me.kvdpxne.dtm.command.CommandBuilder

fun createCoinsMultiplierCommand(): Command {
  /* Usages:
   * /dtm coins multiplier set <VALUE> [USER_NAME]
   * /dtm coins multiplier show [USER_NAME]
   */
  return CommandBuilder()
    .name("multiplier")
    .hub()
    .children(
      createCoinsMultiplierSetCommand(),
      createCoinsMultiplierShowCommand()
    )
    .build()
}