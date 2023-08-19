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

  fun registerCommands(vararg commands: Command) {
    val commandMap = getCommandMap()
    commands.forEach {
      commandMap.register(
        "dtm", BukkitCommandHandler(
          it.name,
          it.permission,
          it.description,
          it.usage,
          it.aliases.toList()
        )
      )
    }
  }
}