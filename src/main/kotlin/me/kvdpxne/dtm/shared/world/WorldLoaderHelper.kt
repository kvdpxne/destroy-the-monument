package me.kvdpxne.dtm.shared.world

import java.io.File
import java.lang.reflect.Field
import java.lang.reflect.Method
import me.kvdpxne.dtm.shared.reflection.Reflection
import me.kvdpxne.notchity.MinecraftVersionCreator
import org.bukkit.Bukkit
import org.bukkit.World
import org.bukkit.World.Environment
import org.bukkit.WorldCreator
import org.bukkit.WorldType

object WorldLoaderHelper {

  private fun prepareWorld(world: World) {
    world.fullTime = 6000L

    // org.bukkit.craftbukkit.v1_7_R4.CraftWorld
    if (MinecraftVersionCreator.getMinecraftVersion().isOlderThanOrEqual(10710)) {
      val craftWorldClass: Class<*> = Reflection.getCraftBukkitClass("CraftWorld")
      val craftWorld: Any = craftWorldClass.cast(world)
      val getHandleField: Method = craftWorldClass.getMethod("getHandle")

      // net.minecraft.server.v1_7_R4.WorldServer
      val nmsWorldServer: Any = getHandleField.invoke(craftWorld)
      val nmsWorldServerClass: Class<*> = nmsWorldServer.javaClass

      // net.minecraft.server.v1_7_R4.WorldData
      val nmsWorldDataField: Field = nmsWorldServerClass.getField("worldData")
      val nmsWorldData: Any = nmsWorldDataField.get(nmsWorldServer)
      val nmsWorldDataClass: Class<*> = nmsWorldData.javaClass
      val setStormMethod: Method = nmsWorldDataClass.getMethod("setStorm", Boolean::class.java)

      setStormMethod.invoke(nmsWorldData, false)
      return
    }

    world.setStorm(false)
    world.weatherDuration = Int.MAX_VALUE
  }

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
      .environment(Environment.NORMAL)
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
      prepareWorld(world)
      return world
    }

    val file = File(Bukkit.getServer().worldContainer, directoryName)
    if (!file.exists()) {
      return null
    }

    world = createWorld(directoryName)
    prepareWorld(world)
    return world
  }
}