package me.kvdpxne.dtm.translation

import java.util.Locale
import me.kvdpxne.dtm.shared.StylishToStringBuilder
import me.kvdpxne.dtm.translation.message.Message
import me.kvdpxne.dtm.translation.message.MessageKey

/**
 * Holds messages specific to a particular [Locale], facilitating localized
 * message retrieval and management.
 *
 * @param locale The locale associated with these messages.
 * @param messages A map associating each [MessageKey] with its corresponding
 * localized [Message].
 *
 * @since 0.1.0
 */
class LocaleMessages(
  // @formatter:off
  val locale  : Locale,
  val messages: Map<MessageKey, Message<*>>
  // @formatter:on
) {

  /**
   * Retrieves a [Message] by its [key], or returns `null` if the key does
   * not exist in this locale.
   *
   * @param key The key for the desired message.
   * @return The message associated with the key, or `null` if not found.
   * @since 0.1.0
   */
  fun findMessageOrNull(key: MessageKey): Message<*>? {
    return this.messages[key]
  }

  /**
   * Retrieves a [Message] by its [key]. Throws an [IllegalArgumentException]
   * if the key is not found.
   *
   * @param key The key for the desired message.
   * @throws IllegalArgumentException If the message key is not found.
   * @return The message associated with the key.
   * @since 0.1.0
   */
  fun findMessage(key: MessageKey): Message<*> {
    return requireNotNull(this.findMessageOrNull(key)) {
      "Message with key '$key' not found in locale '${locale.displayName}'."
    }
  }

  /**
   * Checks equality based solely on the [locale], as this is the unique
   * identifier for a set of locale messages.
   *
   * @param other The other object to compare.
   * @return `true` if both objects have the same locale.
   * @since 0.1.0
   */
  override fun equals(
    other: Any?
  ): Boolean {
    if (this === other) {
      return true
    }

    if (this.javaClass != other?.javaClass) {
      return false
    }

    other as LocaleMessages
    return this.locale == other.locale
  }

  /**
   * Computes the hash code based on the [locale], ensuring consistency with
   * the equality check.
   *
   * @return The hash code of the locale.
   * @since 0.1.0
   */
  override fun hashCode(): Int {
    return this.locale.hashCode()
  }

  /**
   * Provides a stylish string representation of this [LocaleMessages] instance.
   *
   * @return A string describing the locale and associated messages.
   * @since 0.1.0
   */
  override fun toString(): String {
    return StylishToStringBuilder().begin("LocaleMessages")
      .add("locale", this.locale)
      .add("messages", this.messages)
      .build()
  }
}