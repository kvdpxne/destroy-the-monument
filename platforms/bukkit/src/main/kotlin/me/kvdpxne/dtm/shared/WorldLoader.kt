package me.kvdpxne.dtm.shared

import java.io.File
import org.bukkit.Bukkit
import org.bukkit.World
import org.bukkit.WorldCreator
import org.bukkit.WorldType

object WorldLoader {

  /**
   * Creates a new Minecraft world with the specified name, using a void chunk
   * generator to ensure the world is empty (contains no blocks or structures).
   *
   * The world is generated with the following characteristics:
   * - Uses the `VoidChunkGenerator` to create a world with no blocks.
   * - Structures are disabled.
   * - The environment is set to normal (overworld).
   * - The world type is set to normal (default terrain generation rules,
   *   although the void generator overrides block generation).
   *
   * @param name The name of the world to be created.
   * @return The newly created world.
   *
   * @since 0.1.0
   */
  private fun createWorld(
    name: String
  ): World {
    return WorldCreator(name)
      .generator(VoidChunkGenerator.INSTANCE)
      .generateStructures(false)
      .environment(World.Environment.NORMAL)
      .type(WorldType.NORMAL)
      .createWorld()
  }

  /**
   * @throws IllegalArgumentException
   *
   * @since 0.1.0
   */
  fun getWorld(
    directoryName: String
  ): World? {
    require(directoryName.isNotBlank()) {
      "The directory name must not be blank."
    }

    var world: World? = Bukkit.getWorld(directoryName)
    if (null != world) {
      return world
    }

    val file = File(Bukkit.getServer().worldContainer, directoryName)
    if (!file.exists()) {
      return null
    }

    world = createWorld(directoryName)
    return world
  }
}