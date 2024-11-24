package me.kvdpxne.dtm.arena.map

import me.kvdpxne.dtm.arena.ArenaException

/**
 * @since 0.1.0
 */
open class ArenaMapException(
  // @formatter:off
  message: String     = "",
  cause  : Throwable? = null
  // @formatter:on
) : ArenaException(message, cause) {

  companion object {

    /**
     * Serial version UID for serialization compatibility.
     *
     * @since 0.1.0
     */
    @Suppress("ConstPropertyName")
    private const val serialVersionUID: Long = 1450370118356522309L
  }
}