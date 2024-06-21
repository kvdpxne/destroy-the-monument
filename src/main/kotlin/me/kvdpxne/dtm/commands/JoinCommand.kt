package me.kvdpxne.dtm.commands

import me.kvdpxne.dtm.command.Command
import me.kvdpxne.dtm.command.CommandBuilder
import me.kvdpxne.dtm.game.GameManager
import me.kvdpxne.dtm.gui.createGameSelectionGui
import me.kvdpxne.dtm.gui.createTeamSelectionGui
import me.kvdpxne.dtm.user.UserPerformer

fun createJoinCommand(): Command = CommandBuilder()
  .name("join")
  .parent("dtm")
  .handler<UserPerformer> { performer, _ ->
    val player = performer.getPlayer() ?: return@handler

    val game = GameManager.games.values.find {
      it.isInGame(performer.user)
    }

    if (null != game) {
      createTeamSelectionGui(game, performer.user).open(player)
      return@handler
    }

    createGameSelectionGui(performer.user).open(player)
  }
  .build()