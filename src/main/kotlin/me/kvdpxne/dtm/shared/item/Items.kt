package me.kvdpxne.dtm.shared.item

import java.util.Locale
import me.kvdpxne.dtm.placeholder.Placeholders
import me.kvdpxne.dtm.shared.material.toBuilder
import me.kvdpxne.dtm.team.LocalTeam
import me.kvdpxne.dtm.translation.formatter.Formatter
import me.kvdpxne.dtm.translation.message.EnumTranslationKey
import org.bukkit.Material

object Items {

  /**
   * @since 0.1.0
   */
  val stainedClayItem: ItemBuilder by lazy {
    ItemBuilder.begin("STAINED_CLAY")
  }


  /**
   * @since 0.1.0
   */
  val obsidianItem: ItemBuilder by lazy {
    ItemBuilder.begin("OBSIDIAN")
  }

  val webItem: ItemBuilder by lazy {
    ItemBuilder.begin("WEB")
  }

  /**
   * @since 0.1.0
   */
  fun createTeamRepresentationItem(
    team: LocalTeam,
    nominativeTeamName: String,
    genitiveName: String,
    locale: Locale
  ): Any {
    return ItemBuilder.begin("WOOL")
      .generation(team.dyeColor.woolData.toInt())
      .displayName(locale, EnumTranslationKey.CONTAINER_TEAMS_ITEM_BASIC_NAME) {
        Formatter.begin(3)
          .with(Placeholders.TEAM_NAME, nominativeTeamName)
          .with(Placeholders.TEAM_SIZE, team.size)
          .with(Placeholders.TEAM_SIZE_LIMIT, Int.MAX_VALUE)
      }
      .lore(locale, EnumTranslationKey.CONTAINER_TEAMS_ITEM_BASIC_LORE) {
        Formatter.begin(1)
          .with(Placeholders.TEAM_NAME, genitiveName)
      }
      .raw()
  }

  /**
   * @since 0.1.0
   */
  fun createRandomTeamItem(
    locale: Locale
  ): Any {
    return obsidianItem.copy()
      .displayName(locale, EnumTranslationKey.CONTAINER_TEAMS_ITEM_RANDOM_NAME)
      .lore(locale, EnumTranslationKey.CONTAINER_TEAMS_ITEM_RANDOM_LORE)
      .raw()
  }

  fun createGameLeaveItem(
    locale: Locale
  ): Any {
    return this.webItem.copy()
      .displayName(locale, EnumTranslationKey.ITEM_LEAVE_GAME_NAME)
      .lore(locale, EnumTranslationKey.ITEM_LEAVE_GAME_LORE)
      .raw()
  }

  /**
   * @since 0.1.0
   */
  fun createTeamLeaveItem(locale: Locale): Any {
    return Material.WEB.toBuilder()
      .displayName(locale, EnumTranslationKey.ITEM_TEAM_LEAVE_NAME)
      .lore(locale, EnumTranslationKey.ITEM_LEAVE_TEAM_LORE)
      .raw()
  }
}