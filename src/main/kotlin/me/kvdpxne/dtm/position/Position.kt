package me.kvdpxne.dtm.position

/**
 * @since 0.1.0
 */
interface Position<T : Number> {

  /**
   * @since 0.1.0
   */
  val x: T

  /**
   * @since 0.1.0
   */
  val y: T

  /**
   * @since 0.1.0
   */
  val z: T

  /**
   * @since 0.1.0
   */
  val worldName: String?

  /**
   * @since 0.1.0
   */
  fun isNear(
    x: T,
    y: T,
    z: T,
    radius: T
  ): Boolean

  /**
   * @since 0.1.0
   */
  fun isNear(
    position: Position<T>,
    radius: T
  ): Boolean {
    return this.isNear(position.x, position.y, position.z, radius)
  }
}