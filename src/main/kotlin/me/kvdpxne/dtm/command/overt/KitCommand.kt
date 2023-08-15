package me.kvdpxne.dtm.command.overt

import me.kvdpxne.dtm.command.Executor
import me.kvdpxne.dtm.command.Parameter
import me.kvdpxne.dtm.gui.createProfessionSelectionGui
import me.kvdpxne.dtm.user.UserPerformer

object KitCommand : Executor<UserPerformer> {

  override fun execute(performer: UserPerformer, parameter: Parameter) {
    performer.getPlayer()?.let {
      createProfessionSelectionGui(performer.user).open(it)
    }
  }
}