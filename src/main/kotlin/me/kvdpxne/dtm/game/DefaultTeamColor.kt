package me.kvdpxne.dtm.game

import org.bukkit.ChatColor
import org.bukkit.DyeColor

enum class DefaultTeamColor(
  override val identifiableName: String,
  val chatColor: ChatColor,
  val dyeColor: DyeColor
) : TeamColor {

  BLUE("blue", ChatColor.BLUE, DyeColor.BLUE),
  RED("red", ChatColor.DARK_RED, DyeColor.RED),
  NONE("none", ChatColor.WHITE, DyeColor.WHITE)
}