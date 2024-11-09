package me.kvdpxne.dtm.translation

import java.util.Locale
import me.kvdpxne.dtm.command.Performer
import me.kvdpxne.dtm.translation.formatter.Formatter
import me.kvdpxne.dtm.translation.message.Message
import me.kvdpxne.dtm.translation.sender.SendChoices

/**
 * @param receivers
 * @param messages
 *
 * @since 0.1.0
 */
class MessageFormatterChains internal constructor(
  // @formatter:off
  private val receivers: MutableCollection<Performer>,
  private val messages : MutableMap<Locale, Message<*>>
  // @formatter:on
) {

  /**
   * @since 0.1.0
   */
  fun format(
    formatter: Formatter
  ): SendChoices {
    for ((locale: Locale, message: Message<*>) in this.messages) {
      val formattedMessage: Message<*> = message.format(formatter)
      this.messages[locale] = formattedMessage
    }
    return SendChoices(this.receivers, this.messages)
  }

  /**
   * @since 0.1.0
   */
  fun withoutFormat(): SendChoices {
    return SendChoices(this.receivers, this.messages)
  }
}