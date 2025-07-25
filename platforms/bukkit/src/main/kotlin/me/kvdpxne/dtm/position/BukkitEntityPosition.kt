package me.kvdpxne.dtm.position

import java.util.UUID
import me.kvdpxne.dtm.position.diemension.BukkitDimension
import me.kvdpxne.dtm.position.diemesion.Dimension

class BukkitEntityPosition(
  // @formatter:off
  x              : Double,
  y              : Double,
  z              : Double,
  pitch          : Float,
  yaw            : Float,
  worldIdentifier: UUID,
  worldName      : String
  // @formatter:on
) :
  BasicEntityPosition(
    x,
    y,
    z,
    pitch,
    yaw
  ) {

  companion object {

    /**
     * @since 0.1.0
     */
    @Suppress("ConstPropertyName")
    private const val serialVersionUID: Long = -5650296392322839697L
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

  override fun getWorld(): Any? {
    return this.lazyDimension.world
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