package me.kvdpxne.dtm.shared

import org.bukkit.ChatColor

object BukkitTextFormatter : TextFormatter {

  override fun format(text: String): String {
    return ChatColor.translateAlternateColorCodes('&', text)
  }
}