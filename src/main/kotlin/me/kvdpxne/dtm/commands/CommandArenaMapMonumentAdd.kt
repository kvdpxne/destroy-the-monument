package me.kvdpxne.dtm.commands

import me.kvdpxne.dtm.arena.Arena
import me.kvdpxne.dtm.arena.ArenaService
import me.kvdpxne.dtm.command.Command
import me.kvdpxne.dtm.command.CommandBuilder
import me.kvdpxne.dtm.command.CommandException
import me.kvdpxne.dtm.command.Parameters
import me.kvdpxne.dtm.configuration.GeneralConfiguration
import me.kvdpxne.dtm.position.BlockPosition
import me.kvdpxne.dtm.position.MonumentPositionImpl
import me.kvdpxne.dtm.team.Team
import me.kvdpxne.dtm.team.TeamService
import me.kvdpxne.dtm.user.LocalUserPerformer

/**
 * @since 0.1.0
 */
fun createArenaMapMonumentAddCommand(): Command<LocalUserPerformer> {
  // Usage: /dtm arena map monument add <ARENA_NAME> <TEAM_IDENTITY>
  return CommandBuilder.begin<LocalUserPerformer>("add")
    .parameter(
      Parameters.arenaNameParameter()
        .required()
        .build()
    )
    .parameter(
      Parameters.teamNameParameter()
        .required()
        .build()
    )
    .handler { performer, parameters ->
      //
      val position: BlockPosition = performer.user.cache.selectedMonumentPosition
        ?: throw CommandException(
          "&cBłąd: &cNie wybrano zaznaczono żadnego bloku monumentu.\n" +
            "&eINFO: &7Użyj &a/dtm wand &7aby móc zaznaczyć blok monumentu."
        )

      // Unikalna nazwa areny.
      val arenaName: String = parameters[0] as String

      //
      val arena: Arena = ArenaService.findArenaByName(arenaName)
        ?: throw CommandException(
          GeneralConfiguration.NO_FOUND_ARENA
            .replace("{ARENA_NAME}", arenaName)
        )

      // Unikalna nazwa drużyny.
      val teamName: String = parameters[1] as String

      //
      val team: Team = TeamService.findTeamByName(teamName)
        ?: throw CommandException(
          GeneralConfiguration.NO_FOUND_TEAM
            .replace("{TEAM_NAME}", teamName)
        )

      //
      ArenaService.insertArenaMonumentPosition(
        arena,
        MonumentPositionImpl(
          position.x,
          position.y,
          position.z,
          team
        )
      )

      performer.sendMessage(
        "&6&lDTM &7> &7Dodano nowy blok monumentu dla" +
          "drużyny ${team.displayName} &7na arenie o nazwie &a${arena.name}&7."
      )
    }
    .build()
}