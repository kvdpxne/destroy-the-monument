package me.kvdpxne.dtm.commands

import me.kvdpxne.dtm.command.Command
import me.kvdpxne.dtm.command.CommandBuilder
import me.kvdpxne.dtm.shared.item.ItemsClipboard
import me.kvdpxne.dtm.user.LocalUserPerformer

/**
 * @since 0.1.0
 */
fun createWandCommand(): Command<LocalUserPerformer> {
  // Usage: /dtm wand
  return CommandBuilder.begin<LocalUserPerformer>("wand")
    .aliases("w")
    .handler { performer, _ ->
      performer.player?.inventory?.addItem(ItemsClipboard.ITEM_WAND)
    }
    .build()
}