package me.kvdpxne.dtm.command

import org.bukkit.Bukkit

class BukkitConsolePerformer : Performer {

  override val name: String
    get() = Bukkit.getConsoleSender().name

  override fun hasPermission(permission: String): Boolean {
    return true
  }

  override fun sendMessage(message: String) {
    Bukkit.getConsoleSender().sendMessage(message)
  }
}