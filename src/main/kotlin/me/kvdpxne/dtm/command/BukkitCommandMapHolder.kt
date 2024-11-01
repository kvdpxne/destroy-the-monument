package me.kvdpxne.dtm.command

import java.lang.ref.Reference
import java.lang.ref.WeakReference
import java.lang.reflect.Field
import me.kvdpxne.dtm.shared.debug.Debug
import me.kvdpxne.dtm.shared.reflection.accessWhile
import org.bukkit.Bukkit
import org.bukkit.Server
import org.bukkit.command.CommandMap

/**
 * A singleton to access Bukkit's [CommandMap] through reflection.
 *
 * Uses [WeakReference] to avoid strong references to the command map, which
 * could lead to memory leaks in some Bukkit server environments.
 *
 * @since 0.1.0
 */
internal object BukkitCommandMapHolder {

  /**
   * A weak reference to Bukkit's internal [CommandMap].
   *
   * Lazy initialization with error handling to ensure robustness against
   * API changes.
   *
   * @since 0.1.0
   */
  private val _commandMap: Reference<CommandMap?>? by lazy {
    try {
      val server: Server = Bukkit.getServer()
      val serverClass: Class<*> = server.javaClass
      val field: Field = serverClass.getDeclaredField("commandMap")

      val commandMap: CommandMap? = field.accessWhile {
        field.get(server) as? CommandMap
      }

      val reference: Reference<CommandMap?> = WeakReference(commandMap)

      Debug.log {
        "Successfully accessed CommandMap via reflection."
      }

      reference
    } catch (exception: Throwable) {
      Debug.log {
        "Failed to access CommandMap: ${exception.message}"
      }
      null
    }
  }

  /**
   * Provides access to the [CommandMap] if available; returns `null` if
   * reflection fails or if the map is garbage-collected.
   *
   * @since 0.1.0
   */
  internal val commandMap: CommandMap?
    get() = this._commandMap?.get()
}