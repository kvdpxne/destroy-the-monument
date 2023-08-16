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

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as Profession

    if (name != other.name) return false
    if (identifier != other.identifier) return false

    return true
  }

  override fun hashCode(): Int {
    var result = name.hashCode()
    result = 31 * result + identifier.hashCode()
    return result
  }

  override fun toString(): String {
    return "Profession(name='$name', identifier=$identifier)"
  }
}