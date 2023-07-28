package me.kvdpxne.dtm.command.restricted

import me.kvdpxne.dtm.command.Executor
import me.kvdpxne.dtm.command.Parameter
import me.kvdpxne.dtm.game.ArenaManager
import me.kvdpxne.dtm.user.UserPerformer
import java.util.*

object CreateCommand : Executor<UserPerformer> {

  override fun execute(performer: UserPerformer, parameter: Parameter) {
    val player = performer.getPlayer() ?: return

    if (parameter.isEmpty()) {
      performer.sendMessage("The command requires at least 1 argument.")
      return
    }

    val name = parameter.asText()
    ArenaManager.createArena(UUID.randomUUID(), name).let {
      it.world = player.world
      performer.sendMessage("Created")
    }
  }
}