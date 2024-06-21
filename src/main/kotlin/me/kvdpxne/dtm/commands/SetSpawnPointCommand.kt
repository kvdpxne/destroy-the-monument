package me.kvdpxne.dtm.commands

import me.kvdpxne.dtm.command.Command
import me.kvdpxne.dtm.command.CommandBuilder
import me.kvdpxne.dtm.game.ArenaManager
import me.kvdpxne.dtm.game.DefaultTeamColor
import me.kvdpxne.dtm.user.UserPerformer

// Usage: /dtm SetSpawnPoint <ARENA_NAME> <TEAM_IDENTITY>
fun createSetSpawnPointCommand(): Command = CommandBuilder()
  .name("setSpawnPoint")
  .parent("dtm")
  .handler<UserPerformer> { performer, parameter ->
    if (2 > parameter.length()) {
      performer.sendMessage("Usage: /dtm SetSpawnPoint <ARENA_NAME> <TEAM_IDENTITY>")
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
    arena.setSpawnPoint(team, player.location)

    player.sendMessage("Success")
  }
  .build()