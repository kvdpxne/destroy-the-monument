package me.kvdpxne.dtm.command.bukkit

import java.lang.ref.Reference
import java.lang.ref.WeakReference
import me.kvdpxne.dtm.command.Command
import org.bukkit.Bukkit
import org.bukkit.command.CommandMap

object BukkitCommandMapAccessor {

  private var commandMapReference: Reference<CommandMap> = WeakReference(null)

  fun getCommandMap(): CommandMap {
    var commandMap = commandMapReference.get()

    if (null == commandMap) {
      val server = Bukkit.getServer()
      val field = server.javaClass.getDeclaredField("commandMap")
      field.isAccessible = true
      commandMap = field.get(server) as CommandMap
      commandMapReference = WeakReference(commandMap)
      field.isAccessible = false
    }
    return commandMap
  }

  fun registerCommands(command: Command) {
    val commandMap = this.getCommandMap()

    commandMap.register(
      command.name,
      BukkitCommandHandler(
        command.name,
        command.permission,
        command.description,
        command.usage,
        command.aliases.toList(),
        command.executionType
      )
    )
  }
}