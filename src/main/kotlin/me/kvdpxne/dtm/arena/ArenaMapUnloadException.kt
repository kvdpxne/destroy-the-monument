package me.kvdpxne.dtm.arena

/**
 * Exception thrown when an error occurs during the unloading of an arena map.
 *
 * This class extends [ArenaException] to provide specific context for issues
 * related to unloading arena maps in the game.
 *
 * @param message The detail message for the exception, which can provide
 *                information about the error.
 *
 * @since 0.1.0
 */
class ArenaMapUnloadException(message: String) : ArenaException(message) {

  companion object {

    /**
     * Serial version UID for serialization compatibility.
     *
     * @since 0.1.0
     */
    @Suppress("ConstPropertyName")
    private const val serialVersionUID: Long = 8256285322088095734L
  }
}