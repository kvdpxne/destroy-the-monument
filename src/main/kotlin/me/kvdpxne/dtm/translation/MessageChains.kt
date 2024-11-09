package me.kvdpxne.dtm.translation

import java.util.Locale
import me.kvdpxne.dtm.command.ConsolePerformer
import me.kvdpxne.dtm.command.Performer
import me.kvdpxne.dtm.translation.message.EnumMessageKey
import me.kvdpxne.dtm.translation.message.Message
import me.kvdpxne.dtm.translation.message.MessageKey
import me.kvdpxne.dtm.user.LocalUserPerformer

/**
 * @since 0.1.0
 */
class MessageChains internal constructor(
  // @formatter:off
  private val receivers: MutableCollection<Performer>,
  private val messages : MutableMap<Locale, Message<*>> = HashMap()
  // @formatter:on
) {

  /**
   * @param locale
   * @param key
   *
   * @since 0.1.0
   */
  private fun addMessage(
    locale: Locale,
    key: MessageKey
  ) {
    // Attempts to find messages according to the locale provide
    // by the receiver(s).
    val message: Message<*> = TranslationService
      .findLocalMessages(locale)
      .findMessage(key)

    this.messages[locale] = message
  }

  /**
   * @since 0.1.0
   */
  fun message(
    key: MessageKey
  ): MessageFormatterChains {
    for (receiver in this.receivers) {
      if (receiver is LocalUserPerformer) {
        this.addMessage(receiver.locale, key)
        continue
      }

      if (receiver is ConsolePerformer) {
        this.addMessage(TranslationService.defaultLocale, key)
        continue
      }

      throw IllegalStateException(
        "Unsupported receiver type found when trying to search for "
          + "local messages"
      )
    }
    return MessageFormatterChains(this.receivers, this.messages)
  }

  /**
   * @since 0.1.0
   */
  fun message(
    key: EnumMessageKey
  ): MessageFormatterChains {
    return this.message(key.messageKey)
  }
}