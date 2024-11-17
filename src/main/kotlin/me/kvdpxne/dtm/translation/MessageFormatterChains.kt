package me.kvdpxne.dtm.translation

import java.util.Locale
import me.kvdpxne.dtm.command.Performer
import me.kvdpxne.dtm.shared.Copyable
import me.kvdpxne.dtm.translation.formatter.Formatter
import me.kvdpxne.dtm.translation.message.Message
import me.kvdpxne.dtm.translation.sender.SendChoices

/**
 * A class representing a chain of formatted messages to be sent to multiple receivers.
 *
 * This class builds upon [MessageChains] by allowing the formatting of messages for all receivers
 * in the chain before they are sent. The messages can be optionally formatted using a [Formatter],
 * or sent without any formatting.
 *
 * @param receivers A collection of [Performer] objects who will receive the message(s).
 * @param messages A map of locales to their respective [Message] objects, representing the localized messages.
 * @since 0.1.0
 */
class MessageFormatterChains internal constructor(
  // @formatter:off
  private val receivers: MutableCollection<Performer>,
  private val messages : MutableMap<Locale, Message<*>>
  // @formatter:on
) : Copyable<MessageFormatterChains> {

  /**
   * Formats the messages for all receivers in the chain using the provided [formatter].
   *
   * This method applies the given formatter to each message in the chain. The formatted messages are
   * then updated in the [messages] map, with the same locales.
   *
   * @param formatter The [Formatter] that will be used to format the messages.
   * @return A [SendChoices] object that contains the formatted messages and the receivers.
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
   * Returns the messages without applying any formatting.
   *
   * This method allows the messages to be sent in their raw, unformatted state.
   *
   * @return A [SendChoices] object that contains the original (unformatted) messages and the receivers.
   * @since 0.1.0
   */
  fun withoutFormat(): SendChoices {
    return SendChoices(this.receivers, this.messages)
  }

  /**
   * Creates a copy of this [MessageFormatterChains] object.
   *
   * This method creates a new [MessageFormatterChains] object with a copy of the receivers and messages
   * so that changes to the copied object do not affect the original object.
   *
   * @return A new [MessageFormatterChains] object that is a copy of the current one.
   * @since 0.1.0
   */
  override fun copy(): MessageFormatterChains {
    return MessageFormatterChains(
      this.receivers.toMutableList(),
      this.messages.toMutableMap()
    )
  }
}