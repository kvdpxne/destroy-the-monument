package me.kvdpxne.dtm.implementations.bukkit;

import me.kvdpxne.dtm.PluginContextKt;
import org.bukkit.plugin.java.JavaPlugin;

public class BukkitPluginBootLoader /*extends JavaPlugin*/ {

  public BukkitPluginBootLoader() {
    PluginContextKt.setPositionConverter(new BukkitPositionConverter());
    PluginContextKt.setTextFormatter(new BukkitTextFormatter());
  }
}
