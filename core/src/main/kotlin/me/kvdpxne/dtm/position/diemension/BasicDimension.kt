package me.kvdpxne.dtm.position.diemension

import java.lang.ref.Reference
import java.util.UUID
import me.kvdpxne.dtm.position.diemesion.Dimension
import org.jetbrains.annotations.Range

open class BasicDimension protected constructor(
  // @formatter:off
  protected val identifier: UUID,
  protected val name      : String
  // @formatter:on
) : Dimension {

  companion object {

    /**
     * @since 0.1.0
     */
    @Suppress("ConstPropertyName")
    private const val serialVersionUID: Long = -2902419495550652401L
  }

  /**
   * @since 0.1.0
   */
  protected var worldReference: Reference<Any>? = null

  override fun getWorldIdentifier(): UUID {
    return this.identifier
  }

  override fun getWorldName(): String {
    return this.name
  }

  override fun getWorld(): Any {
    throw NotImplementedError()
  }

  override fun getType(): @Range(from = 0, to = 127) Byte {
    TODO("Not yet implemented")
  }

  override fun sameWorld(
    worldIdentifier: UUID
  ): Boolean {
    return this.worldIdentifier == worldIdentifier
  }

  override fun sameWorld(
    worldName: String
  ): Boolean {
    return this.worldName == worldName
  }

  override fun sameWorld(
    world: Any
  ): Boolean {
    val lazedWorld: Any? = this.world
    if (null != lazedWorld) {
      return lazedWorld == world
    }

    // If the world to which the position is referring is equal to NULL then it
    // is not possible to determine whether the given world is the same as that
    // to which the position is referring.
    return false
  }

  override fun sameWorld(dimension: Dimension): Boolean {
    val lazedWorld: Any? = dimension.world
    if (null != lazedWorld) {
      return this.sameWorld(lazedWorld)
    }

    return false
  }

  override fun copy(): Dimension {
    return BasicDimension(
      this.identifier,
      this.name
    )
  }

  /**
   * @since 0.1.0
   */
  fun clearWorld() {
    this.worldReference = null
  }

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (other !is BasicDimension) return false

    if (identifier != other.identifier) return false
    if (name != other.name) return false

    return true
  }

  override fun hashCode(): Int {
    var result = identifier.hashCode()
    result = 31 * result + name.hashCode()
    return result
  }
}