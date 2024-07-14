package me.kvdpxne.dtm.commands

import me.kvdpxne.dtm.command.Command
import me.kvdpxne.dtm.command.CommandBuilder
import me.kvdpxne.dtm.command.ParameterBuilder
import me.kvdpxne.dtm.command.ParameterValidators
import me.kvdpxne.dtm.game.GameManager
import me.kvdpxne.dtm.user.UserPerformer

fun createGlobalChatCommand(): Command {
  // Usage: /g <TEXT...>
  return CommandBuilder()
    .name("globalChat")
    .aliases("global", "g")
    .parameter(
      ParameterBuilder<String>()
        .name("full_text")
        .validationBy(ParameterValidators.STRING_VALIDATOR)
        .required()
        .varargs()
        .build()
    )
    .handler<UserPerformer> { performer, arguments ->
      val game = GameManager.findByUser(performer.user)
      if (null == game) {
        performer.sendMessage("You're not in any game.")
        return@handler
      }

      game.hostages.values.forEach {
        it.performer.sendMessage(
          "&7[&6G&7] &6${performer.name}&7: &f${arguments.asFullText()}"
        )
      }
    }
    .build()
}