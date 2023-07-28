package me.kvdpxne.dtm.command.overt

import me.kvdpxne.dtm.command.Executor
import me.kvdpxne.dtm.command.Parameter
import me.kvdpxne.dtm.gui.builtin.KitGui
import me.kvdpxne.dtm.user.UserPerformer

object KitCommand : Executor<UserPerformer> {

  override fun execute(performer: UserPerformer, parameter: Parameter) {
    performer.getPlayer().let {
      it ?: return
      KitGui().open(it)
    }
  }
}