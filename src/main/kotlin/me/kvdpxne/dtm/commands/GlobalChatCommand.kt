package me.kvdpxne.dtm.commands

import me.kvdpxne.dtm.command.Command
import me.kvdpxne.dtm.command.CommandBuilder
import me.kvdpxne.dtm.game.GameManager
import me.kvdpxne.dtm.user.UserPerformer

fun createGlobalChatCommand(): Command = CommandBuilder()
  .name("globalChat")
  .aliases("global", "g")
  .handler<UserPerformer> { performer, parameters ->
    if (parameters.isEmpty()) {
      performer.sendMessage("Usage: /g <MESSAGE>")
      return@handler
    }

    val game = GameManager.findByUser(performer.user)
    if (null == game) {
      performer.sendMessage("You're not in any game.")
      return@handler
    }

    game.hostages.values.forEach {
      it.performer.sendMessage(
        "&7[&6G&7] &6${performer.name}&7: &f${parameters.asText()}"
      )
    }
  }
  .build()