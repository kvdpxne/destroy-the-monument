package me.kvdpxne.dtm.user.cache

import me.kvdpxne.dtm.position.BlockPosition
import me.kvdpxne.dtm.position.EntityPosition

/**
 * A cache for storing temporary, user-specific data for a [me.kvdpxne.dtm.user.LocalUser]. This
 * interface provides properties  to manage the user's selected monument
 * position and teleportation history, enabling efficient access to
 * frequently modified data that doesn't require persistence.
 *
 * @since 0.1.0
 */
interface LocalUserCache {

  /**
   * The currently selected monument position for the user, represented by a
   * [BlockPosition].
   *
   * This value may be null if no monument is selected or if the selection has
   * been cleared.
   *
   * @since 0.1.0
   */
  var selectedMonumentPosition: BlockPosition?

  /**
   * A history of teleportation positions for the user, stored as an
   * [ArrayDeque] of [EntityPosition] objects.
   *
   * This collection allows tracking of the user's recent movement, providing
   * functionality for features such as "teleport back" or movement analysis.
   *
   * @since 0.1.0
   */
  val teleportationHistory: ArrayDeque<EntityPosition>
}