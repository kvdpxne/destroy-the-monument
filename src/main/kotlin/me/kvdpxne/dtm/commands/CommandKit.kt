package me.kvdpxne.dtm.commands

import me.kvdpxne.dtm.command.Command
import me.kvdpxne.dtm.command.CommandBuilder
import me.kvdpxne.dtm.guis.createProfessionSelectionGui
import me.kvdpxne.dtm.user.LocalUserPerformer

/**
 * @since 0.1.0
 */
fun createKitCommand(): Command<LocalUserPerformer> {
  return CommandBuilder.begin<LocalUserPerformer>("kit")
    .aliases("kits", "class", "classes", "profession", "professions")
    .handler { performer, _ ->
      performer.player?.let {
        createProfessionSelectionGui(performer.user).open(it)
      }
    }
    .build()
}