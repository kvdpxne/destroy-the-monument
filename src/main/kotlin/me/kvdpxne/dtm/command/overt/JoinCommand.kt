package me.kvdpxne.dtm.command.overt

import me.kvdpxne.dtm.command.Executor
import me.kvdpxne.dtm.command.Parameter
import me.kvdpxne.dtm.game.GameManager
import me.kvdpxne.dtm.gui.builtin.createGameSelectionGui
import me.kvdpxne.dtm.gui.builtin.createTeamSelectionGui
import me.kvdpxne.dtm.user.UserPerformer

object JoinCommand : Executor<UserPerformer> {

  override fun execute(performer: UserPerformer, parameter: Parameter) {
    val player = performer.getPlayer() ?: return

    val game = GameManager.games.values.find {
      it.isInGame(performer.user)
    }

    if (null != game) {
      createTeamSelectionGui(game, performer.user).open(player)
      return
    }
    createGameSelectionGui(performer.user).open(player)
    return
  }
}