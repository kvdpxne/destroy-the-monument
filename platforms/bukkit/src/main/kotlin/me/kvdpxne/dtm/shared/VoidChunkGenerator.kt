package me.kvdpxne.dtm.shared

import java.util.Random
import org.bukkit.Location
import org.bukkit.World
import org.bukkit.generator.ChunkGenerator

/**
 * A chunk generator that generates empty or "void" chunks, used for creating
 * a world with no blocks. This class is particularly useful for minigames or
 * specific world setups where an empty world is required.
 *
 * @since 0.1.0
 */
internal class VoidChunkGenerator : ChunkGenerator() {

  companion object {

    /**
     * Singleton instance of the void chunk generator.
     *
     * @since 0.1.0
     */
    internal val INSTANCE: ChunkGenerator = VoidChunkGenerator()
  }

  override fun getFixedSpawnLocation(world: World?, random: Random?): Location {
    return Location(world, 0.0, 128.5, 0.0)
  }



  /**
   * Generates an empty byte array for a given chunk. This method ensures that
   * the chunk is void and contains no block data.
   *
   * @param world The world in which the chunk is being generated.
   * @param random A random number generator for creating randomized elements in chunks.
   * @param x The X-coordinate of the chunk.
   * @param z The Z-coordinate of the chunk.
   * @return An empty byte array, indicating a void chunk.
   *
   * @since 0.1.0
   */
  override fun generate(
    world: World,
    random: Random,
    x: Int,
    z: Int
  ): ByteArray {
    return byteArrayOf()
  }

  /**
   * Generates an empty array of block sections using byte arrays. Like the extended block
   * sections, this method returns an empty array to ensure that no blocks are generated in
   * the chunk.
   *
   * @param world The world in which the chunk is being generated.
   * @param random A random number generator used for adding randomness in block generation.
   * @param x The X-coordinate of the chunk.
   * @param z The Z-coordinate of the chunk.
   * @param biomes The biome data grid for the chunk.
   * @return An empty array of byte arrays, indicating a void chunk.
   *
   * @since 0.1.0
   */
  override fun generateBlockSections(
    world: World,
    random: Random,
    x: Int,
    z: Int,
    biomes: BiomeGrid
  ): Array<ByteArray> {
    return emptyArray()
  }

  /**
   * Generates an empty array of block sections using short arrays. This method is
   * responsible for generating block data in extended format, but returns an empty array
   * to signify a void chunk.
   *
   * @param world The world in which the chunk is being generated.
   * @param random A random number generator for adding randomness in block generation.
   * @param x The X-coordinate of the chunk.
   * @param z The Z-coordinate of the chunk.
   * @param biomes The biome data grid for the chunk.
   * @return An empty array of short arrays, indicating no blocks are present.
   *
   * @since 0.1.0
   */
  override fun generateExtBlockSections(
    world: World,
    random: Random,
    x: Int,
    z: Int,
    biomes: BiomeGrid
  ): Array<ShortArray> {
    return emptyArray()
  }
}