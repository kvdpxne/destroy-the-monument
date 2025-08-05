package me.kvdpxne.dtm.profession

import me.kvdpxne.dtm.shared.material.toBuilder
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

class SlotItem(val item: ItemStack, val index: Int)

fun slotItem(material: Material, amount: Int = 1, index: Int) = SlotItem(ItemStack(material, amount), index)
fun slotItem(item: () -> ItemStack, index: Int) = SlotItem(item(), index)
fun slotItem(item: ItemStack, index: Int) = SlotItem(item, index)


fun slotArmor(material: Material, index: Int) = SlotItem(
  material.toBuilder().unbreakable().build(),
  index
)

fun slotHelmet(material: Material) = slotArmor(material, 39)
fun slotChestplate(material: Material) = slotArmor(material, 38)
fun slotLeggings(material: Material) = slotArmor(material, 37)
fun slotBoots(material: Material) = slotArmor(material, 36)