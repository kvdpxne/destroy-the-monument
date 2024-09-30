package me.kvdpxne.dtm.game

/**
 * @since 0.1.0
 */
interface Teamable<T : Team> {

  /**
   * @since 0.1.0
   */
  val team: T
}