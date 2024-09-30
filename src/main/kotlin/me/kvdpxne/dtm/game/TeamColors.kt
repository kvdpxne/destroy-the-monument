package me.kvdpxne.dtm.game

import org.bukkit.ChatColor
import org.bukkit.DyeColor

object TeamColors {

  val AQUA  : TeamColor = Triple(ChatColor.AQUA, ChatColor.DARK_AQUA, DyeColor.CYAN)
  val BLACK : TeamColor = Triple(ChatColor.DARK_GRAY, ChatColor.BLACK, DyeColor.BLACK)
  val BLUE  : TeamColor = Triple(ChatColor.BLUE, ChatColor.DARK_BLUE, DyeColor.BLUE)
  val GREEN : TeamColor = Triple(ChatColor.GREEN, ChatColor.DARK_GREEN, DyeColor.LIME)
  val PINK  : TeamColor = Triple(ChatColor.LIGHT_PURPLE, ChatColor.DARK_PURPLE, DyeColor.PINK)
  val RED   : TeamColor = Triple(ChatColor.RED, ChatColor.DARK_RED, DyeColor.RED)
  val YELLOW: TeamColor = Triple(ChatColor.YELLOW, ChatColor.GOLD, DyeColor.YELLOW)
  val WHITE : TeamColor = Triple(ChatColor.WHITE, ChatColor.GRAY, DyeColor.WHITE)

  /**
   * @since 0.1.0
   */
  val entries: Map<String, TeamColor> = mapOf(
    "aqua" to AQUA,
    "black" to BLACK,
    "blue" to BLUE,
    "green" to GREEN,
    "pink" to PINK,
    "red" to RED,
    "yellow" to YELLOW,
    "white" to WHITE
  )

  /**
   * @since 0.1.0
   */
  val names: List<String>
    get() = entries.keys.toList()

  /**
   * @since 0.1.0
   */
  fun findTeamColorByName(name: String): TeamColor? {
    return entries[name.lowercase()]
  }
}