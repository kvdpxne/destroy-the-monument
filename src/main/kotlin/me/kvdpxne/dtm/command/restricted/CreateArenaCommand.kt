package me.kvdpxne.dtm.command.restricted

import me.kvdpxne.dtm.command.Executor
import me.kvdpxne.dtm.command.Parameter
import me.kvdpxne.dtm.game.ArenaManager
import me.kvdpxne.dtm.user.UserPerformer

object CreateArenaCommand : Executor<UserPerformer> {

  // Usage: /dtm CreateArena <ARENA_NAME>
  override fun execute(performer: UserPerformer, parameter: Parameter) {
    if (1 > parameter.length()) {
      performer.sendMessage("Usage: /dtm create <game|arena> <name>")
      return
    }

    val arenaName = parameter.asText()
    ArenaManager.createArena(arenaName)
    performer.sendMessage("Success")
  }
}