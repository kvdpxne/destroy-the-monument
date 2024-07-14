package me.kvdpxne.dtm.commands

import me.kvdpxne.dtm.command.Command
import me.kvdpxne.dtm.command.CommandBuilder

fun createBaseCommand(): Command {
  /* Usages:
   * /dtm arena
   * /dtm coins
   * /dtm game
   * /dtm help
   * /dtm join <GAME_NAME|TEAM_NAME>
   * /dtm kit
   * /dtm leave
   * /dtm teleport <MAP_NAME>
   * /dtm teleportBack
   * /dtm wand
   */
  return CommandBuilder()
    .name("dtm")
    .hub()
    .children(
      createArenaCommand(),
      createCoinsCommand(),
      createGameCommand(),
      createHelpCommand(),
      createJoinCommand(),
      createKitCommand(),
      createLeaveCommand(),
      createTeleportCommand(),
      createTeleportCommand(),
      createVersionCommand(),
      createWandCommand()
    )
    .build()
}