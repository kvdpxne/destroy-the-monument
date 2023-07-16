package me.kvdpxne.dtm.kit

import me.kvdpxne.dtm.gui.SlotItem
import org.bukkit.inventory.ItemStack
import java.util.*

open class Kit(
  val identifier: UUID = UUID.randomUUID(),
  var name: String,
  var displayName: String? = null,
  val items: List<SlotItem>,
  val category: KitCategory,
  val icon: ItemStack
)