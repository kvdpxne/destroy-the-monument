package me.kvdpxne.dtm.game

import me.kvdpxne.dtm.shared.IdentifiableByName
import org.bukkit.ChatColor
import org.bukkit.DyeColor

enum class DefaultTeamColor(
  override val identifiableName: String,
  val chatColor: ChatColor,
  val dyeColor: DyeColor
) : IdentifiableByName {

  BLUE("blue", ChatColor.BLUE, DyeColor.BLUE),
  RED("red", ChatColor.DARK_RED, DyeColor.RED);

  companion object {
    fun viaIdentity(identity: String): DefaultTeamColor? {
      return entries.find { it.identifiableName == identity }
    }
  }
}