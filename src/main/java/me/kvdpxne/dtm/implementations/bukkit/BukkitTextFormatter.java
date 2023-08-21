package me.kvdpxne.dtm.implementations.bukkit;

import me.kvdpxne.dtm.shared.TextFormatter;
import org.bukkit.ChatColor;
import org.jetbrains.annotations.NotNull;

public class BukkitTextFormatter implements TextFormatter {

  @NotNull
  @Override
  public String format(@NotNull String text) {
    return ChatColor.translateAlternateColorCodes('&', text);
  }
}
