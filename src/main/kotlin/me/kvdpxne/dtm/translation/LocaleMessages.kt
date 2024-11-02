package me.kvdpxne.dtm.translation

import java.util.Locale
import me.kvdpxne.dtm.translation.message.MessageKey

/**
 * @param locale
 * @param messages
 *
 * @since 0.1.0
 */
class LocaleMessages(
  // @formatter:off
  val locale  : Locale,
  val messages: Map<MessageKey, String>
  // @formatter:on
) {

  /**
   * @since 0.1.0
   */
  fun findMessageOrNull(key: MessageKey): String? {
    return this.messages[key]
  }

  /**
   *
   * @throws IllegalArgumentException
   * @since 0.1.0
   */
  fun findMessage(key: MessageKey): String {
    return requireNotNull(this.findMessageOrNull(key)) {
      ""
    }
  }

  override fun equals(other: Any?): Boolean {
    if (this === other) {
      return true
    }

    if (this.javaClass != other?.javaClass) {
      return false
    }

    other as LocaleMessages
    return this.locale == other.locale
  }

  override fun hashCode(): Int {
    return this.locale.hashCode()
  }
}