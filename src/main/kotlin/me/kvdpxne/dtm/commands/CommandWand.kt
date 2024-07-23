package me.kvdpxne.dtm.commands

import me.kvdpxne.dtm.command.Command
import me.kvdpxne.dtm.command.CommandBuilder
import me.kvdpxne.dtm.shared.ItemsClipboard
import me.kvdpxne.dtm.user.UserPerformer

fun createWandCommand(): Command {
  // Usage: /dtm wand
  return CommandBuilder()
    .name("wand")
    .aliases("w")
    .handler<UserPerformer> { performer, _ ->
      performer.player?.inventory?.addItem(ItemsClipboard.ITEM_WAND)
    }
    .build()
}