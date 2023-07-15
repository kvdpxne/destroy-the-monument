package me.kvdpxne.dtm.kit

import org.bukkit.Material
import org.bukkit.inventory.ItemStack

class SlotItem(val item: ItemStack, val index: Int)

fun slotItem(material: Material, amount: Int = 1, index: Int) = SlotItem(ItemStack(material, amount), index)
fun slotItem(item: () -> ItemStack, index: Int) = SlotItem(item(), index)