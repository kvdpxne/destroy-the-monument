package me.kvdpxne.dtm.game

import java.util.UUID
import me.kvdpxne.dtm.shared.ancillary.Identifiable
import org.bukkit.World

interface ArenaMap : Identifiable<UUID> {

  /**
   * @since 0.1.0
   */
  var name: String

  /**
   * @since 0.1.0
   */
  var world: World?

  /**
   * @since 0.1.0
   */
  val isLoaded: Boolean

  /**
   * @since 0.1.0
   */
  fun load()

  /**
   * @since 0.1.0
   */
  fun unload(): Boolean
}