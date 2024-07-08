package me.kvdpxne.dtm.game

import org.bukkit.ChatColor
import org.bukkit.DyeColor

data class TeamIdentity(
  val identifier: String,
  val name: String,
  val colorInChat: ChatColor,
  val professionColor: ChatColor,
  val dyeColor: DyeColor
)