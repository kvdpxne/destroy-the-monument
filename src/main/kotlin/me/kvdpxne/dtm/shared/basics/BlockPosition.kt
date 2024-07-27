package me.kvdpxne.dtm.shared.basics

interface BlockPosition : Position<Int> {

  /**
   * @since 0.1.0
   */
  fun isIn(x: Int, y: Int, z: Int): Boolean

  /**
   * @since 0.1.0
   */
  fun isIn(position: BlockPosition): Boolean {
    return this.isIn(position.x, position.y, position.z)
  }
}