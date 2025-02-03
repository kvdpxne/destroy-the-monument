package me.kvdpxne.dtm.position

open class BasicBlockPosition(
  private val x: Int,
  private val y: Int,
  private val z: Int
) :
  BasicPosition(),
  BlockPosition {

  companion object {
    /**
     * @since 0.1.0
     */
    @Suppress("ConstPropertyName")
    private const val serialVersionUID: Long = 5737040253236214515L
  }

  override fun getX(): Int {
    return this.x
  }

  override fun getY(): Int {
    return this.y
  }

  override fun getZ(): Int {
    return this.z
  }

  override fun isNear(
    x: Int,
    y: Int,
    z: Int,
    radius: Int
  ): Boolean {
    return this.x in x - radius..x + radius &&
      this.y in y - radius..y + radius &&
      this.z in z - radius..z + radius
  }

  override fun isNear(
    position: BlockPosition,
    radius: Int
  ): Boolean {
    return this.isNear(position.x, position.y, position.z, radius)
  }

  override fun isIn(
    x: Int,
    y: Int,
    z: Int
  ): Boolean {
    return this.x == x && this.y == y && this.z == z
  }

  override fun isIn(
    position: BlockPosition
  ): Boolean {
    return this.isIn(position.x, position.y, position.z)
  }

  override fun isMultidimensional(): Boolean {
    return true
  }

  override fun copy(): BlockPosition {
    return BasicBlockPosition(
      this.x,
      this.y,
      this.z
    )
  }
}