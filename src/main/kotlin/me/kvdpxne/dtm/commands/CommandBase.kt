package me.kvdpxne.dtm.commands

import me.kvdpxne.dtm.command.Command
import me.kvdpxne.dtm.command.CommandBuilder
import me.kvdpxne.dtm.command.Performer

/**
 * @since 0.1.0
 */
fun createBaseCommand(): Command<Performer> {
  /* Usages:
   * /dtm ability
   * /dtm arena
   * /dtm coins
   * /dtm game
   * /dtm help
   * /dtm join <GAME_NAME|TEAM_NAME>
   * /dtm kit
   * /dtm leave
   * /dtm position
   * /dtm teleport <MAP_NAME>
   * /dtm teleportBack
   * /dtm wand
   */
  return CommandBuilder.begin<Performer>("dtm")
    .hub()
    .children(
      createAbilityCommand(),
      createArenaCommand(),
      createCoinsCommand(),
      createGameCommand(),
      createHelpCommand(),
      createJoinCommand(),
      createKitCommand(),
      createLeaveCommand(),
      createPositionCommand(),
      createTeamCommand(),
      createTeleportCommand(),
      createTeleportBackCommand(),
      createVersionCommand(),
      createWandCommand()
    )
    .build()
}