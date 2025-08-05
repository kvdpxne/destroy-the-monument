package me.kvdpxne.dtm.game

import me.kvdpxne.dtm.Providable

/**
 * @since 0.1.0
 */
interface LocalGameProvider : Providable {

  /**
   * @since 0.1.0
   */
  fun toLocalGame(): LocalGame
}