package me.kvdpxne.dtm.shared

import me.kvdpxne.dtm.shared.minecraft.bukkit.Attributes
import me.kvdpxne.dtm.shared.minecraft.bukkit.isTool
import me.kvdpxne.dtm.shared.minecraft.bukkit.toBuilder
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemStack

/**
 * @since 0.1.0
 */
object ItemsClipboard {

  val ITEM_GAME_JOIN = Material.NETHER_STAR.toBuilder()
    .name("&a&lDołącz do gry")
    .build()

  val ITEM_GAME_LEAVE = Material.WEB.toBuilder()
    .name("&c&lOpuść gre")
    .build()

  val ITEM_TEAM_SELECT = Material.NETHER_STAR.toBuilder()
    .name("&e&lWybierz drużyne")
    .build()

  val ITEM_TEAM_SELECT_RANDOM = Material.OBSIDIAN.toBuilder()
    .name("&6Wybierz losową drużyne")
    .lore(
      "&7Zostaniesz dodany do drużyny,",
      "&7w której jest mniej graczy.",
      "",
      "&7Jeżeli wszystkie drużyny mają",
      "&7taką samą liczbę graczy to",
      "&7zostaniesz dodany do losowej",
      "&7drużyny."
    )
    .build()


  val ITEM_PROFESSION_SELECT = Material.IRON_AXE.toBuilder()
    .name("&e&lWybierz profesje")
    .build()

   val LOSE = Material.DEAD_BUSH.toBuilder()
    .name("&c&lPRZEGRAŁEŚ")
    .build()

  val WON = Material.DIAMOND.toBuilder()
    .name("&a&lWYGRAŁEŚ")
    .build()

  val ITEM_TOOL_PICKAXE = Material.DIAMOND_PICKAXE.toBuilder()
    .lore(
      "",
      "&7Narzędzia zadają &cZNACZNIE MNIEJ OBRAŻEŃ",
      "&7niż twoja główna broń ponieważ powinny być",
      "&7wykorzystywane do interakcji z mapą",
      "&7areny i nie powinny być używane do",
      "&7pojedynku między graczami."
    )
    .attribute(Attributes.ATTACK_DAMAGE, 1.25)
    .unbreakable()
    .build()

  val ITEM_TOOL_AXE = Material.IRON_AXE.toBuilder()
    .lore(
      "",
      "&7Narzędzia zadają &cZNACZNIE MNIEJ OBRAŻEŃ",
      "&7niż twoja główna broń ponieważ powinny być",
      "&7wykorzystywane do interakcji z mapą",
      "&7areny i nie powinny być używane do",
      "&7pojedynku między graczami."
    )
    .enchantment(Enchantment.DIG_SPEED, 1)
    .attribute(Attributes.ATTACK_DAMAGE, 1.75)
    .unbreakable()
    .build()

  val ITEM_WAND = Material.STICK.toBuilder()
    .name("&bSpecjalny patyk")
    .lore(
      "&fWygląda jak zwykły patyk a to nie koniec",
      "&fjego zalet, klikając &cLPM &fna blok monumentu",
      "&fpozycja klikniętego bloku zostanie zapisana",
      "&fdo schowka aby można było nią zarządzać.",
      "",
      "&fTen przedmiot jest przydatny do tworzenia",
      "&flub edytowania map aren."
    )
    .build()

  /**
   * @since 0.1.0
   */
  fun makeTool(
    type: Material
  ): ItemStack {
    if (!type.isTool()) {
      throw IllegalArgumentException("The given type is not a tool.")
    }

    return type.toBuilder()
      .lore(
        "&7Narzędzia stworzone przy użyciu",
        "&7Stołu rzemieślniczego są tak samo",
        "&7niezniszczalnego jak narzędzia",
        "&7podstawowe ale zadają tyle samo",
        "&7obrażeń co uderzanie ręką."
      )
      .attribute(Attributes.ATTACK_DAMAGE, 0.0)
      .unbreakable()
      .build()
  }
}