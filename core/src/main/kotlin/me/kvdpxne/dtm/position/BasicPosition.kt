package me.kvdpxne.dtm.position

import java.util.UUID
import me.kvdpxne.dtm.position.diemesion.Dimension

/**
 * @since 0.1.0
 */
abstract class BasicPosition protected constructor() :
  Dimension {

  companion object {

    /**
     * @since 0.1.0
     */
    @Suppress("ConstPropertyName")
    private const val serialVersionUID: Long = -637682547455274278L
  }

  override fun getWorldIdentifier(): UUID {
    TODO("Not yet implemented")
  }

  override fun getWorldName(): String {
    TODO("Not yet implemented")
  }

  override fun getWorld(): Any? {
    TODO("Not yet implemented")
  }

  override fun sameWorld(worldIdentifier: UUID): Boolean {
    TODO("Not yet implemented")
  }

  override fun sameWorld(worldName: String): Boolean {
    TODO("Not yet implemented")
  }

  override fun sameWorld(world: Any): Boolean {
    TODO("Not yet implemented")
  }

  override fun sameWorld(dimension: Dimension): Boolean {
    TODO("Not yet implemented")
  }

  override fun copy(): Dimension {
    TODO("Not yet implemented")
  }
}