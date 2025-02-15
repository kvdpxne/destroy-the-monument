package me.kvdpxne.dtm.translation.message

import me.kvdpxne.dtm.translation.Translation
import me.kvdpxne.dtm.translation.formatter.Formatter

/**
 * Represents a message with content of type [T]. This interface can be used for
 * single or multiple messages and provides support for content formatting.
 *
 * @param T The type of the message content.
 *
 * @see MultipleMessages
 * @see SingleMessage
 * @since 0.1.0
 */
interface Message<T> : Translation {

  /**
   * The content of the message, which could be a single element or an array
   * based on the implementation.
   *
   * @since 0.1.0
   */
  val content: T

  /**
   * Formats the content using the provided [formatter]. This method allows
   * customization of the message's output, supporting different formats or
   * languages.
   *
   * @param formatter The formatter to apply to the message content.
   * @since 0.1.0
   */
  fun format(
    formatter: Formatter
  ): Message<T>
}