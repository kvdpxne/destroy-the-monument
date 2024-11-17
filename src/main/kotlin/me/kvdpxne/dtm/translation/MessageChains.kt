package me.kvdpxne.dtm.translation

import java.util.Locale
import me.kvdpxne.dtm.command.ConsolePerformer
import me.kvdpxne.dtm.command.Performer
import me.kvdpxne.dtm.shared.Copyable
import me.kvdpxne.dtm.translation.message.EnumMessageKey
import me.kvdpxne.dtm.translation.message.Message
import me.kvdpxne.dtm.translation.message.MessageKey
import me.kvdpxne.dtm.user.LocalUserPerformer

/**
 * A class representing a chain of messages to be sent to multiple receivers.
 *
 * This class allows building messages that will be sent to a collection of
 * receivers, with localization support based on the receiver's locale (or
 * default locale for the console).
 *
 * @param receivers A collection of [Performer] objects who will receive the message(s).
 * @param messages A map of locales to their respective [Message] objects, representing the localized messages.
 * @since 0.1.0
 */
class MessageChains internal constructor(
  // @formatter:off
  private val receivers: MutableCollection<Performer>,
  private val messages : MutableMap<Locale, Message<*>> = hashMapOf()
  // @formatter:on
) : Copyable<MessageChains> {

  /**
   * Adds a localized message for a specific locale.
   *
   * This method attempts to find a localized message for the provided
   * locale and key. If found, the message is added to the [messages] map for
   * the given locale.
   *
   * @param locale The locale for which the message is being added.
   * @param key The key that identifies the message to be added.
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
   * Adds a message for the receivers in the chain, based on the provided
   * message key.
   *
   * This method checks the type of each receiver in the chain (whether it is
   * a [LocalUserPerformer] or [ConsolePerformer]), and adds the appropriate
   * localized message for each receiver.
   *
   * @param key The [MessageKey] that identifies the message to be sent to the receivers.
   * @return A [MessageFormatterChains] object that allows further formatting and message manipulation.
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

      error("Unsupported receiver type found when trying to search for local messages.")
    }
    return MessageFormatterChains(this.receivers, this.messages)
  }

  /**
   * Adds a message for the receivers in the chain, based on the
   * provided [EnumMessageKey].
   *
   * This method is similar to the other `message` method but is specifically
   * designed to handle messages that are represented by an enum type.
   *
   * @param key The [EnumMessageKey] that identifies the message to be sent to the receivers.
   * @return A [MessageFormatterChains] object that allows further formatting and message manipulation.
   * @since 0.1.0
   */
  fun message(
    key: EnumMessageKey
  ): MessageFormatterChains {
    return this.message(key.messageKey)
  }

  /**
   * Creates a copy of this [MessageChains] object.
   *
   * This method creates a new [MessageChains] object with a copy of the
   * receivers and messages so that changes to the copied object do not affect
   * the original object.
   *
   * @return A new [MessageChains] object that is a copy of the current one.
   * @since 0.1.0
   */
  override fun copy(): MessageChains {
    return MessageChains(
      this.receivers.toMutableList(),
      this.messages.toMutableMap()
    )
  }
}