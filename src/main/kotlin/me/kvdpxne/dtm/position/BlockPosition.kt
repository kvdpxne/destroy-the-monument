package me.kvdpxne.dtm.position

interface BlockPosition : Position<Int> {

  /**
   * @since 0.1.0
   */
  fun isIn(
    x: Int,
    y: Int,
    z: Int
  ): Boolean {
    return this.x == x && this.y == y && this.z == z
  }

  /**
   * @since 0.1.0
   */
  fun isIn(
    position: BlockPosition
  ): Boolean {
    return this.isIn(position.x, position.y, position.z)
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
}