package me.kvdpxne.dtm.command

import java.lang.ref.Reference
import java.lang.ref.WeakReference
import java.lang.reflect.Field
import org.bukkit.Bukkit
import org.bukkit.Server
import org.bukkit.command.CommandMap

/**
 * @since 0.1.0
 */
internal object BukkitCommandMapHolder {

  /**
   * @since 0.1.0
   */
  private val _commandMap: Reference<CommandMap> by lazy {
    val server: Server = Bukkit.getServer()
    val field: Field = server.javaClass.getDeclaredField("commandMap")

    field.trySetAccessible()
    val commandMap: CommandMap = field.get(server) as CommandMap

    field.isAccessible = false

    WeakReference(commandMap)
  }

  /**
   * @since 0.1.0
   */
  internal val commandMap: CommandMap?
    get() = this._commandMap.get()
}