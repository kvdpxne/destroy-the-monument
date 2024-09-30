package me.kvdpxne.dtm.commands

import me.kvdpxne.dtm.command.Command
import me.kvdpxne.dtm.command.CommandBuilder
import me.kvdpxne.dtm.gui.createGameSelectionGui
import me.kvdpxne.dtm.gui.createTeamSelectionGui
import me.kvdpxne.dtm.user.LocalUserPerformer

/**
 * @since 0.1.0
 */
fun createJoinCommand(): Command<LocalUserPerformer> {
  return CommandBuilder.begin<LocalUserPerformer>("join")
    .handler { performer, _ ->
      val player = performer.player ?: return@handler

      val game = performer.user.game

      if (null != game) {
        createTeamSelectionGui(game, performer.user).open(player)
        return@handler
      }

      createGameSelectionGui(performer.user).open(player)
    }
    .build()
}