package me.kvdpxne.dtm.commands

import me.kvdpxne.dtm.command.Command
import me.kvdpxne.dtm.command.CommandBuilder
import me.kvdpxne.dtm.command.CommandException
import me.kvdpxne.dtm.command.Parameters
import me.kvdpxne.dtm.game.Arena
import me.kvdpxne.dtm.game.ArenaService
import me.kvdpxne.dtm.game.MonumentPositionImpl
import me.kvdpxne.dtm.game.Team
import me.kvdpxne.dtm.game.TeamService
import me.kvdpxne.dtm.shared.basics.position.BlockPosition
import me.kvdpxne.dtm.user.LocalUserPerformer

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
        ?: throw CommandException("&cBŁĄD: &7Nie wybrano zaznaczono żadnego bloku monumentu.\n" +
          "&eINFO: &7Użyj &a/dtm wand &7aby móc zaznaczyć blok monumentu.")

      //
      val arenaName: String = parameters[0] as String

      //
      val arena: Arena = ArenaService.findArenaByName(arenaName)
        ?: throw CommandException("&cBłąd: &7Arena o nazwie: &c$arenaName &7nie istnieje.")

      //
      val teamName: String = parameters[1] as String

      //
      val team: Team = TeamService.findTeamByName(teamName)
        ?: throw CommandException("&cBłąd: &7Drużyna o nazwie: &c$teamName &7nie istnieje.")

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

      performer.sendMessage("&6&lDTM &7> &7Dodano nowy blok monumentu dla" +
        "drużyny ${team.displayName} &7na arenie o nazwie &a${arenaName}&7."
      )
    }
    .build()
}