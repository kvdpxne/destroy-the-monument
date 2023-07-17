package me.kvdpxne.dtm.command.restricted

import me.kvdpxne.dtm.command.Executor
import me.kvdpxne.dtm.command.Parameter
import me.kvdpxne.dtm.user.UserPerformer
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

val wand = ItemStack(Material.STICK).apply {
  val meta = itemMeta
  meta.displayName = "Wand"
  meta.lore = listOf("A special item to facilitate the creation of arenas.")
  itemMeta = meta
}

object WandCommand : Executor<UserPerformer> {

  override fun execute(performer: UserPerformer, parameter: Parameter) {
    performer.getPlayer()?.inventory?.addItem(wand)
  }
}