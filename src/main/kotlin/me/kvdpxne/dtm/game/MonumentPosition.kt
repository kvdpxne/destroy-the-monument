package me.kvdpxne.dtm.game

import java.util.UUID
import me.kvdpxne.dtm.shared.ancillary.Identifiable
import me.kvdpxne.dtm.shared.basics.position.BlockPosition

/**
 * @since 0.1.0
 */
interface MonumentPosition<T : Team> :
  Identifiable<UUID>, BlockPosition, Teamable<T> {

  /**
   * @since 0.1.0
   */
  val isDestroyed: Boolean

  /**
   * @since 0.1.0
   */
  fun destroy()

  /**
   * @since 0.1.0
   */
  fun restore()
}