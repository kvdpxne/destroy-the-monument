package me.kvdpxne.dtm

import net.minecraft.server.v1_7_R4.PacketPlayInChat
import net.minecraft.server.v1_7_R4.PacketPlayOutChat
import net.minecraft.server.v1_8_R3.PacketPlayInTabComplete
import net.minecraft.server.v1_8_R3.PacketPlayOutTabComplete
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

class BukkitEntry : JavaPlugin() {

  override fun onLoad() {
    PacketPlayInChat
    PacketPlayOutChat

  }

  override fun onEnable() {

  }

  override fun onDisable() {

  }
}