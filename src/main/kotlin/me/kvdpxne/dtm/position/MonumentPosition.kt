package me.kvdpxne.dtm.position

import me.kvdpxne.dtm.shared.Identifiable
import me.kvdpxne.dtm.shared.MonumentPositionUuid
import me.kvdpxne.dtm.team.Team
import me.kvdpxne.dtm.team.Teamable

/**
 * @since 0.1.0
 */
interface MonumentPosition<T : Team> :
  Identifiable<MonumentPositionUuid>, BlockPosition, Teamable<T> {

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