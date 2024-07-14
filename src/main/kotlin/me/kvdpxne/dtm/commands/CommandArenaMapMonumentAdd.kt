package me.kvdpxne.dtm.commands

import me.kvdpxne.dtm.command.Command
import me.kvdpxne.dtm.command.CommandBuilder
import me.kvdpxne.dtm.game.ArenaManager
import me.kvdpxne.dtm.game.TeamService
import me.kvdpxne.dtm.game.addMonument
import me.kvdpxne.dtm.shared.SelectedPositionStorage
import me.kvdpxne.dtm.user.UserPerformer

// Usage: /dtm AddMonument <ARENA_NAME> <TEAM_IDENTITY>
fun createArenaMapMonumentAddCommand(): Command {
  return CommandBuilder()
    .name("addMonument")
    .handler<UserPerformer> { performer, parameter ->

      val arenaName = parameter.asText()
      val arena = ArenaManager.findArenaByName(arenaName)

      if (null == arena) {
        performer.sendMessage("An arena named $arenaName does not exist.")
        return@handler
      }

      val teamName = parameter.asText(1)
      val team = TeamService.findTeamIdentity(teamName)

      if (null == team) {
        performer.sendMessage("An team named $teamName does not exist.")
        return@handler
      }

      val player = performer.getPlayer() ?: return@handler
      val position = SelectedPositionStorage.selectedBlocks[player.uniqueId]

      if (null == position) {
        performer.sendMessage("No block is selected.")
        return@handler
      }

      arena.addMonument(team, position)
      player.sendMessage("Success")
    }
    .build()
}