package me.kvdpxne.dtm.arena

/**
 * Thrown to indicate that an arena could not be found.
 *
 * @param message the detail message. Defaults to "Failed to find an arena."
 * @param cause the cause of the exception. Defaults to `null`.
 *
 * @since 0.1.0
 */
open class ArenaNoFoundException(
  // @formatter:off
  message: String     = "Failed to find an arena.",
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
    private const val serialVersionUID: Long = 1656065159401641722L
  }
}