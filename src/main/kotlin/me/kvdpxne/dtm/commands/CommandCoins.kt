package me.kvdpxne.dtm.commands

import me.kvdpxne.dtm.command.Command
import me.kvdpxne.dtm.command.CommandBuilder
import me.kvdpxne.dtm.command.Performer

/**
 * @since 0.1.0
 */
fun createCoinsCommand(): Command<Performer> {
  /* Usages:
   * /dtm coins add <VALUE> [USER_NAME]
   * /dtm coins multiplier [set|show]
   * /dtm coins set <VALUE> [USER_NAME]
   * /dtm coins show [USER_NAME]
   * /dtm coins subtract <VALUE> [USER_NAME]
   * /dtm coins top
   */
  return CommandBuilder.begin<Performer>("coins")
    .aliases("money", "balance", "bal")
    .hub()
    .children(
      createCoinsAddCommand(),
      createCoinsMultiplierCommand(),
      createCoinsSetCommand(),
      createCoinsShowCommand(),
      createCoinsSubtractCommand(),
      createCoinsTopCommand()
    )
    .build()
}
