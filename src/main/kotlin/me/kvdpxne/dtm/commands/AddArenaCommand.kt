package me.kvdpxne.dtm.commands

import me.kvdpxne.dtm.command.Command
import me.kvdpxne.dtm.command.CommandBuilder
import me.kvdpxne.dtm.game.ArenaManager
import me.kvdpxne.dtm.game.GameManager
import me.kvdpxne.dtm.user.UserPerformer

// Usage: /dtm AddArena <ARENA_NAME> <GAME_NAME>
fun createAddArenaCommand(): Command = CommandBuilder()
  .name("addArena")
  .parent("dtm")
  .handler<UserPerformer> { performer, parameter ->
    if (2 > parameter.length()) {
      performer.sendMessage("Usage: /dtm AddArena <ARENA_NAME> <GAME_NAME>")
      return@handler
    }

    val arenaName = parameter.asText()
    val arena = ArenaManager.findArenaByName(arenaName)

    if (null == arena) {
      performer.sendMessage("An arena named $arenaName does not exist.")
      return@handler
    }

    val gameName = parameter.asText(1)
    val game = GameManager.findByName(gameName)

    if (null == game) {
      performer.sendMessage("An game named $gameName does not exist.")
      return@handler
    }

    game.addArena(arena)
    performer.sendMessage("Success")
  }
  .build()