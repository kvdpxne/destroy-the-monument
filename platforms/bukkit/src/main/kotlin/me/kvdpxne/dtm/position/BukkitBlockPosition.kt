package me.kvdpxne.dtm.position

import java.util.UUID
import me.kvdpxne.dtm.position.diemension.BukkitDimension
import me.kvdpxne.dtm.position.diemesion.Dimension

class BukkitBlockPosition(
  // @formatter:off
  x              : Int,
  y              : Int,
  z              : Int,
  worldIdentifier: UUID,
  worldName      : String
  // @formatter:on
) :
  BasicBlockPosition(
    x,
    y,
    z,
  ),
  BlockPosition {

  companion object {

    /**
     * @since 0.1.0
     */
    @Suppress("ConstPropertyName")
    private const val serialVersionUID: Long = 8123959476770302793L
  }

  /**
   * @since 0.1.0
   */
  private val lazyDimension: Dimension by lazy {
    BukkitDimension(worldIdentifier, worldName)
  }

  override fun getWorldIdentifier(): UUID {
    return this.lazyDimension.worldIdentifier
  }

  override fun getWorldName(): String {
    return this.lazyDimension.worldName
  }

  override fun getWorldOrNull(): Any? {
    return this.lazyDimension.worldOrNull
  }

  override fun sameWorld(worldIdentifier: UUID): Boolean {
    return this.lazyDimension.sameWorld(worldIdentifier)
  }

  override fun sameWorld(worldName: String): Boolean {
    return this.lazyDimension.sameWorld(worldName)
  }

  override fun sameWorld(world: Any): Boolean {
    return this.lazyDimension.sameWorld(world)
  }

  override fun sameWorld(dimension: Dimension): Boolean {
    return this.lazyDimension.sameWorld(dimension)
  }

  override fun isMultidimensional(): Boolean {
    return false
  }
}