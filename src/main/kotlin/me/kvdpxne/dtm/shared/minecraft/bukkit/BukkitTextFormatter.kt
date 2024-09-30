package me.kvdpxne.dtm.shared.minecraft.bukkit

import me.kvdpxne.dtm.shared.TextFormatter
import org.bukkit.ChatColor

object BukkitTextFormatter : TextFormatter {

  /**
   * @since 0.1.0
   */
  override fun format(
    text: String
  ): String {
    if (text.isEmpty() || text.isBlank()) {
      return text
    }
    return ChatColor.translateAlternateColorCodes('&', text)
  }

  /**
   * @since 0.1.0
   */
  override fun format(
    texts: Array<String>
  ): Array<String> {
    if (texts.isEmpty()) {
      return texts
    }

    for (i: Int in texts.indices) {
      texts[i] = this.format(texts[i])
    }

    return texts
  }

  /**
   * @since 0.1.0
   */
  override fun format(
    texts: Collection<String>
  ): Collection<String> {
    if (texts.isEmpty()) {
      return texts
    }

    return texts
      .map { text: String ->
        this.format(text)
      }
      .toList()
  }
}