package me.kvdpxne.dtm.profession

import java.util.UUID
import me.kvdpxne.dtm.gui.SlotItem
import org.bukkit.DyeColor
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.LeatherArmorMeta

class Profession(
  var name: String,
  var displayName: String,
  var items: List<SlotItem>,
  var icon: ItemStack,
  val identifier: UUID = UUID.randomUUID()
) {

  fun equip(player: Player, dyeColor: DyeColor) {
    items.forEach {
      val item = it.item
      val meta = item.itemMeta
      if (meta is LeatherArmorMeta) {
        meta.color = dyeColor.color
        item.itemMeta = meta
      }
      player.inventory.setItem(it.index, item)
    }
  }
}