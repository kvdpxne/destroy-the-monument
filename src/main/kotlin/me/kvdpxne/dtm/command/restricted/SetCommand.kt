package me.kvdpxne.dtm.command.restricted

import me.kvdpxne.dtm.command.Executor
import me.kvdpxne.dtm.command.Parameter
import me.kvdpxne.dtm.game.ArenaManager
import me.kvdpxne.dtm.game.DefaultTeamColor
import me.kvdpxne.dtm.game.GameManager
import me.kvdpxne.dtm.user.UserPerformer

object SetCommand : Executor<UserPerformer> {

  // dtm set test monument test5
  override fun execute(performer: UserPerformer, parameter: Parameter) {
    val player = performer.getPlayer() ?: return
    if (2 > parameter.length()) {
      return
    }

    val name = parameter.asText()
    val type = parameter.asText(1)
    val additional = parameter.asText(2)

    val game = GameManager.findGameByName(name) ?: return
    if ("arena".equals(type, true)) {
      game.arena = ArenaManager.getArenaByName(additional)
      return
    }

    if ("monument".equals(type, true)) {
      val arena = game.arena ?: return
      arena.addMonument(
        DefaultTeamColor.findByIdentityKey(additional)!!,
        player.location.subtract(0.0, -1.0, 0.0)
      )
      return
    }

    if ("respawn".equals(type, true)) {
      val arena = game.arena ?: return
      arena.addMonument(
        DefaultTeamColor.findByIdentityKey(additional)!!,
        player.location
      )
    }
  }
}