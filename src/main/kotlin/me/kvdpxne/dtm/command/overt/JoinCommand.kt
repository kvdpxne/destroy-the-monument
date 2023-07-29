package me.kvdpxne.dtm.command.overt

import me.kvdpxne.dtm.command.Executor
import me.kvdpxne.dtm.command.Parameter
import me.kvdpxne.dtm.game.DefaultTeamColor
import me.kvdpxne.dtm.game.GameManager
import me.kvdpxne.dtm.gui.builtin.TeamSelectionGui
import me.kvdpxne.dtm.user.UserPerformer

object JoinCommand : Executor<UserPerformer> {

  override fun execute(performer: UserPerformer, parameter: Parameter) {
    val player = performer.getPlayer() ?: return

    if (parameter.isEmpty()) {
      TeamSelectionGui.open(player)
      return
    }

    val name = parameter.asText()
    val game = GameManager.findGameByArenaName(name)

    if (null == game) {
      performer.sendMessage("")
      return
    }

    game.addTeammate(DefaultTeamColor.entries.random(), performer.user)
  }
}