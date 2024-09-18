package me.kvdpxne.dtm.commands

import me.kvdpxne.dtm.command.Command
import me.kvdpxne.dtm.command.CommandBuilder
import me.kvdpxne.dtm.command.CommandException
import me.kvdpxne.dtm.command.Parameters
import me.kvdpxne.dtm.game.Arena
import me.kvdpxne.dtm.game.ArenaService
import me.kvdpxne.dtm.game.RevivalPositionImpl
import me.kvdpxne.dtm.game.Team
import me.kvdpxne.dtm.game.TeamService
import me.kvdpxne.dtm.user.LocalUserPerformer
import org.bukkit.Location

fun createArenaMapRevivalSetCommand(): Command<LocalUserPerformer> {
  // Usage: /dtm arena map revival set <ARENA_NAME> <TEAM_NAME>
  return CommandBuilder.begin<LocalUserPerformer>("set")
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
      val location: Location = performer.player!!.location

      //
      //
      ArenaService.insertArenaRevivalPosition(
        arena,
        //
        RevivalPositionImpl(
          location.x,
          location.y,
          location.z,
          location.pitch,
          location.yaw,
          team
        )
      )

      performer.sendMessage("")
    }
    .build()
}