package me.kvdpxne.dtm.commands

import me.kvdpxne.dtm.command.Command
import me.kvdpxne.dtm.command.CommandBuilder
import me.kvdpxne.dtm.game.ArenaService
import me.kvdpxne.dtm.game.RevivalPosition
import me.kvdpxne.dtm.game.TeamService
import me.kvdpxne.dtm.user.UserPerformer

fun createArenaMapRevivalSetCommand(): Command {
  // Usage: /dtm arena map revival set <ARENA_NAME> <TEAM_IDENTITY>
  return CommandBuilder()
    .name("set")
    .handler<UserPerformer> { performer, parameter ->

      val arenaName = parameter.asText()
      val arena = ArenaService.findArenaByName(arenaName)

      if (null == arena) {
        performer.sendMessage("&cBłąd: &7Arena o nazwie: &c$arenaName &7nie istnieje.")
        return@handler
      }

      val teamName = parameter.asText(1)
      val team = TeamService.findTeamIdentityByName(teamName)

      if (null == team) {
        performer.sendMessage("&cBłąd: &7Drużyna o nazwie: &c$teamName &7nie istnieje.")
        return@handler
      }

      val location = performer.player?.location ?: return@handler

      //
      //
      ArenaService.insertArenaRevivalPosition(
        arena,
        //
        RevivalPosition(
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