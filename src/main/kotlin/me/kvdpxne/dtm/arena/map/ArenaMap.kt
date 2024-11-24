package me.kvdpxne.dtm.arena.map

import me.kvdpxne.dtm.shared.Identifiable
import me.kvdpxne.dtm.shared.WorldUuid
import org.bukkit.World

/**
 * Represents a map associated with an arena in the game.
 *
 * The [ArenaMap] interface extends [Identifiable] to provide a unique
 * identifier for each map.
 *
 * @since 0.1.0
 */
interface ArenaMap : Identifiable<WorldUuid> {

  /**
   * The name of the map.
   *
   * @since 0.1.0
   */
  var name: String

  /**
   * The Bukkit world associated with the map, if available.
   *
   * @since 0.1.0
   */
  var world: World?

  /**
   * Indicates whether the map is currently loaded in the game.
   *
   * @since 0.1.0
   */
  val isLoaded: Boolean

  /**
   * Loads the map into the game, making it accessible for gameplay.
   *
   * @since 0.1.0
   */
  fun load()

  /**
   * Unloads the map from the game, freeing up resources.
   *
   * This function attempts to unload the current arena map. If the unloading
   * is successful, it returns `true`; otherwise, it returns `false`.
   * An `ArenaMapUnloadException` may be thrown if an error occurs during
   * the unloading process.
   *
   * @return `true` if the map was successfully unloaded, `false` otherwise.
   * @throws ArenaMapUnloadException if an error occurs while unloading the map.
   *
   * @since 0.1.0
   */
  @Throws(ArenaMapUnloadException::class)
  fun unload(): Boolean

  /**
   * @since 0.1.0
   */
  fun save(): Boolean
}