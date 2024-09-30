package me.kvdpxne.dtm.command

import org.bukkit.Bukkit

/**
 * @since 0.1.0
 */
internal class BukkitConsolePerformer private constructor(): ConsolePerformer {

  companion object {

    /**
     * @since 0.1.0
     */
    val INSTANCE: ConsolePerformer = BukkitConsolePerformer()
  }

  /**
   * @since 0.1.0
   */
  override val name: String
    get() = Bukkit.getConsoleSender().name

  /**
   * @since 0.1.0
   */
  override fun sendMessage(message: String) {
    Bukkit.getConsoleSender().sendMessage(message)
  }

  /**
   * @since 0.1.0
   */
  override fun sendMessage(message: () -> String) {
    val context = message()
    Bukkit.getConsoleSender().sendMessage(context)
  }

  /**
   * @since 0.1.0
   */
  override fun sendMessages(messages: Array<out String>) {
    val sender = Bukkit.getConsoleSender()
    messages.forEach {
      sender.sendMessage(it)
    }
  }
}