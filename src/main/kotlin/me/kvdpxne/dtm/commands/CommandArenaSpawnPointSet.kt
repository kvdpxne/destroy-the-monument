package me.kvdpxne.dtm.commands

import me.kvdpxne.dtm.command.Command
import me.kvdpxne.dtm.command.CommandBuilder
import me.kvdpxne.dtm.game.ArenaManager
import me.kvdpxne.dtm.game.TeamService
import me.kvdpxne.dtm.game.setSpawnPoint
import me.kvdpxne.dtm.user.UserPerformer

fun createSetSpawnPointCommand(): Command {
  // Usage: /dtm arena spawnpoint set <ARENA_NAME> <TEAM_IDENTITY>
  return CommandBuilder()
    .name("set")
    .handler<UserPerformer> { performer, parameter ->

      val arenaName = parameter.asText()
      val arena = ArenaManager.findArenaByName(arenaName)

      if (null == arena) {
        performer.sendMessage("An arena named $arenaName does not exist.")
        return@handler
      }

      val teamName = parameter.asText(1)
      val team = TeamService.findTeamIdentityByName(teamName)

      if (null == team) {
        performer.sendMessage("An team named $teamName does not exist.")
        return@handler
      }

      val player = performer.getPlayer() ?: return@handler
      arena.setSpawnPoint(team, player.location)

      player.sendMessage("Success")
    }
    .build()
}