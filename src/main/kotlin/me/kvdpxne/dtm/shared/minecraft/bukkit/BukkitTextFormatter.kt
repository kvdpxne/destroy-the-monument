package me.kvdpxne.dtm.shared.minecraft.bukkit

import me.kvdpxne.dtm.shared.minecraft.TextFormatter
import org.bukkit.ChatColor

object BukkitTextFormatter : TextFormatter {

  override fun format(text: String): String {
    if (text.isEmpty() || text.isBlank()) {
      return text
    }
    return ChatColor.translateAlternateColorCodes('&', text)
  }
}