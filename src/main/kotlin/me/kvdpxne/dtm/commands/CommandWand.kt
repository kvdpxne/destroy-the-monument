package me.kvdpxne.dtm.commands

import me.kvdpxne.dtm.command.Command
import me.kvdpxne.dtm.command.CommandBuilder
import me.kvdpxne.dtm.user.UserPerformer
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

val wand = ItemStack(Material.STICK).apply {
  val meta = itemMeta
  meta.displayName = "Wand"
  meta.lore = listOf("A special item to facilitate the creation of arenas.")
  itemMeta = meta
}

fun createWandCommand(): Command {
  // Usage: /dtm wand
  return CommandBuilder()
    .name("wand")
    .aliases("w")
    .handler<UserPerformer> { performer, _ ->
      performer.getPlayer()?.inventory?.addItem(wand)
    }
    .build()
}