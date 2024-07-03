package me.kvdpxne.dtm.command.bukkit

import me.kvdpxne.dtm.command.Performer
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

  override fun sendMessage(message: () -> String) {
    val context = message()
    Bukkit.getConsoleSender().sendMessage(context)
  }

  override fun sendMessages(messageArray: Array<out String>) {
    val sender = Bukkit.getConsoleSender()
    messageArray.forEach {
      sender.sendMessage(it)
    }
  }
}