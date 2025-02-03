package me.kvdpxne.dtm.position

open class BasicEntityPosition(
  private val x: Double,
  private val y: Double,
  private val z: Double,
  private val pitch: Float,
  private val yaw: Float,
) :
  BasicPosition(),
  EntityPosition {

  companion object {

    /**
     * @since 0.1.0
     */
    @Suppress("ConstPropertyName")
    private const val serialVersionUID: Long = 2851214265023907204L
  }

  override fun getX(): Double {
    return this.x
  }

  override fun getY(): Double {
    return this.y
  }

  override fun getZ(): Double {
    return this.z
  }

  override fun getPitch(): Float {
    return this.pitch
  }

  override fun getYaw(): Float {
    return this.yaw
  }

  override fun isNear(
    x: Double,
    y: Double,
    z: Double,
    radius: Double
  ): Boolean {
    return this.x in x - radius..x + radius &&
      this.y in y - radius..y + radius &&
      this.z in z - radius..z + radius
  }

  override fun isNear(
    position: EntityPosition,
    radius: Double
  ): Boolean {
    return this.isNear(position.x, position.y, position.z, radius)
  }

  override fun isMultidimensional(): Boolean {
    return true
  }

  override fun copy(): EntityPosition {
    return BasicEntityPosition(
      this.x,
      this.y,
      this.z,
      this.pitch,
      this.yaw
    )
  }
}