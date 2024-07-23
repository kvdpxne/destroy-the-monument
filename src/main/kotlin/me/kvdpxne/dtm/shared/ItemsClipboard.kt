package me.kvdpxne.dtm.shared

import me.kvdpxne.dtm.shared.bukkit.toBuilder
import org.bukkit.Material

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
    .name("&c&lPRZEGRALES")
    .build()

  val WON = Material.DIAMOND.toBuilder()
    .name("&a&lWYGRALES")
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
}