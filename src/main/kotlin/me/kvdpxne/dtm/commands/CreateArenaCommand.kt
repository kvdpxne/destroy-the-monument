package me.kvdpxne.dtm.commands

import me.kvdpxne.dtm.command.Command
import me.kvdpxne.dtm.command.CommandBuilder
import me.kvdpxne.dtm.game.ArenaManager
import me.kvdpxne.dtm.user.UserPerformer

// Usage: /dtm CreateArena <ARENA_NAME>
fun createCreateArenaCommand(): Command = CommandBuilder()
  .name("createArena")
  .parent("dtm")
  .handler<UserPerformer> { performer, parameter ->
    if (1 > parameter.length()) {
      performer.sendMessage("Usage: /dtm CreateArena <ARENA_NAME>")
      return@handler
    }

    val arenaName = parameter.asText()
    ArenaManager.createArena(arenaName)
    performer.sendMessage("Success")
  }
  .build()