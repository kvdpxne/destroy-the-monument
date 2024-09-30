package me.kvdpxne.dtm.commands

import me.kvdpxne.dtm.command.Command
import me.kvdpxne.dtm.command.CommandBuilder
import me.kvdpxne.dtm.command.Performer

/**
 * @since 0.1.0
 */
fun createCoinsMultiplierCommand(): Command<Performer> {
  /* Usages:
   * /dtm coins multiplier set <VALUE> [USER_NAME]
   * /dtm coins multiplier show [USER_NAME]
   */
  return CommandBuilder.begin<Performer>("multiplier")
    .hub()
    .children(
      createCoinsMultiplierSetCommand(),
      createCoinsMultiplierShowCommand()
    )
    .build()
}