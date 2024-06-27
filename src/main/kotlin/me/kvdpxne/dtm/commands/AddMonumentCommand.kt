package me.kvdpxne.dtm.commands

import me.kvdpxne.dtm.command.Command
import me.kvdpxne.dtm.command.CommandBuilder
import me.kvdpxne.dtm.command.CommandHandler
import me.kvdpxne.dtm.game.ArenaManager
import me.kvdpxne.dtm.game.DefaultTeamColor
import me.kvdpxne.dtm.game.addMonument
import me.kvdpxne.dtm.shared.SelectedPositionStorage
import me.kvdpxne.dtm.user.UserPerformer

// Usage: /dtm AddMonument <ARENA_NAME> <TEAM_IDENTITY>
fun createAddMonumentCommand(): Command = CommandBuilder()
  .name("addMonument")
  .parent("dtm")
  .handler<UserPerformer> { performer, parameter ->
    if (2 > parameter.length()) {
      performer.sendMessage("Usage: /dtm AddMonument <ARENA_NAME> <TEAM_IDENTITY>")
      return@handler
    }

    val arenaName = parameter.asText()
    val arena = ArenaManager.findArenaByName(arenaName)

    if (null == arena) {
      performer.sendMessage("An arena named $arenaName does not exist.")
      return@handler
    }

    val teamName = parameter.asText(1)
    val team = DefaultTeamColor.findByIdentityKey(teamName)

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