package me.kvdpxne.dtm.professions

import me.kvdpxne.dtm.gui.slotBoots
import me.kvdpxne.dtm.gui.slotChestplate
import me.kvdpxne.dtm.gui.slotHelmet
import me.kvdpxne.dtm.gui.slotItem
import me.kvdpxne.dtm.profession.Profession
import me.kvdpxne.dtm.profession.ProfessionBuilder
import me.kvdpxne.dtm.shared.item.ItemsClipboard.ITEM_TOOL_AXE
import me.kvdpxne.dtm.shared.item.ItemsClipboard.ITEM_TOOL_PICKAXE
import me.kvdpxne.dtm.shared.item.isNullOrTypeAir
import me.kvdpxne.dtm.shared.material.toBuilder
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

fun createEngineer(): Profession = ProfessionBuilder()
  .name("engineer")
  .displayName("Technik")
  .items(
    // Armor
    slotHelmet(Material.LEATHER_HELMET),
    slotChestplate(Material.LEATHER_CHESTPLATE),
    slotBoots(Material.LEATHER_BOOTS),

    // Weapons
    slotItem({
      Material.STONE_SWORD.toBuilder()
        .unbreakable()
        .build()
    }, index = 0),

    // Tools
    slotItem(ITEM_TOOL_AXE, index = 1),
    slotItem(ITEM_TOOL_PICKAXE, index = 2),

    slotItem(Material.POTION, 2, 7),
    slotItem(Material.COBBLESTONE, 50, 8),
  )
  .icon(
    Material.COBBLESTONE.toBuilder()
      .lore(
        "",
        "&7- Budowniczy",
        "&7- Teleporter",
        "&7- Dozownik"
      )
      .build()
  )
  .ability(20, true) {

    val inventory = it.inventory
    val index = inventory.contents.indexOfFirst {
      null != it && it.type == Material.COBBLESTONE && 64 > it.amount
    }

    if (-1 != index) {
      val itemStack = inventory.contents[index]
      val amount = itemStack.amount

      val sum = amount + 15
      if (64 == sum) {
        inventory.setItem(index, ItemStack(Material.COBBLESTONE, sum))
        return@ability
      }

      if (64 < sum) {
        val diff = sum - 64
        inventory.setItem(index, ItemStack(Material.COBBLESTONE, 64))
        val nextIndex = inventory.contents.indexOfFirst { it.isNullOrTypeAir() }
        inventory.setItem(nextIndex, ItemStack(Material.COBBLESTONE, diff))
        return@ability
      }


      inventory.setItem(index, ItemStack(Material.COBBLESTONE, sum))
      return@ability
    }

    val nextIndex = inventory.contents.indexOfFirst { it.isNullOrTypeAir() }
    inventory.setItem(nextIndex, ItemStack(Material.COBBLESTONE, 15))
  }
  .enabled()
  .build()