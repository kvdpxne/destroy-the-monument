package me.kvdpxne.dtm.profession

import me.kvdpxne.dtm.game.Teammate
import me.kvdpxne.dtm.gui.SlotItem
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.LeatherArmorMeta
import java.util.*

open class Kit(
  val identifier: UUID = UUID.randomUUID(),
  var name: String,
  var displayName: String? = null,
  val items: List<SlotItem>,
  val category: KitCategory,
  val icon: ItemStack
) {

  fun equip(teammate: Teammate) {
    teammate.kit = this
    items.forEach {
      val item = it.item
      val meta = item.itemMeta
      if (meta is LeatherArmorMeta) {
        meta.color = teammate.teamColor.dyeColor.color
        item.itemMeta = meta
      }
      teammate.player.inventory.setItem(it.index, item)
    }

  }
}