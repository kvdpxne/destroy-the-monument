package me.kvdpxne.dtm.arena

/**
 * Custom exception class for handling errors related to arenas in the game.
 *
 * This class extends [RuntimeException] to provide a specific exception type
 * for arena-related operations.
 *
 * @param message The detail message for the exception, which can provide
 *                information about the error.
 *
 * @since 0.1.0
 */
open class ArenaException(
  // @formatter:off
  message: String     = "",
  cause  : Throwable? = null
  // @formatter:on
) : RuntimeException(message, cause) {

  companion object {

    /**
     * Serial version UID for serialization compatibility.
     *
     * @since 0.1.0
     */
    @Suppress("ConstPropertyName")
    private const val serialVersionUID: Long = -8396776841121661460L
  }
}