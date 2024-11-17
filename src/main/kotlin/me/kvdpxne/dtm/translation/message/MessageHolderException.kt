package me.kvdpxne.dtm.translation.message

/**
 * @since 0.1.0
 */
class MessageHolderException(
  val context: Message<*>
) : RuntimeException() {

  companion object {

    /**
     * Serial version UID for this exception class.
     *
     * Used to ensure compatibility during serialization and
     * deserialization processes.
     *
     * @since 0.1.0
     */
    @Suppress("ConstPropertyName")
    private const val serialVersionUID: Long = -5394898825877843090L
  }
}