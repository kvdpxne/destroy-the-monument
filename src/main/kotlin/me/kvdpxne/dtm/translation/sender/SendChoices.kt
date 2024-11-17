package me.kvdpxne.dtm.translation.sender

import java.util.Locale
import me.kvdpxne.dtm.command.Performer
import me.kvdpxne.dtm.configuration.GeneralConfiguration
import me.kvdpxne.dtm.translation.locale
import me.kvdpxne.dtm.translation.message.Message
import me.kvdpxne.dtm.translation.message.MultipleMessages
import me.kvdpxne.dtm.translation.message.SingleMessage

/**
 * A class representing the choices of messages to send to a collection of performers.
 *
 * This class encapsulates a set of messages for different locales and provides methods for
 * retrieving and formatting these messages for sending to performers (users or consoles). It
 * also allows customization of whether to add prefixes to messages when sending them via chat.
 *
 * @param performers A collection of [Performer] objects to receive the messages.
 * @param messages A map of locales to the respective [Message] objects, representing the localized messages.
 * @since 0.1.0
 */
class SendChoices(
  // @formatter:off
  private val performers: MutableCollection<Performer>,
  private val messages  : MutableMap<Locale, Message<*>>
  // @formatter:on
) {

  /**
   * Retrieves the original message based on the first performer and their locale.
   *
   * This method checks that exactly one performer and one message exists. It returns the message
   * associated with the performer's locale.
   *
   * @return The original [Message] corresponding to the first performer's locale.
   * @throws IllegalArgumentException if there are not exactly one performer or message in the collection.
   * @since 0.1.0
   */
  fun original(): Message<*> {
    require(1 == this.performers.size && 1 == this.messages.size) {
      "Expected exactly one performer and one message."
    }

    val locale: Locale = this.performers.first().locale
    val message: Message<*> = this.messages[locale]!!

    return message
  }

  /**
   * Retrieves the raw content of the original message.
   *
   * This method returns the raw content of the message, which can be either a [SingleMessage]
   * or a [MultipleMessages]. It casts the content to the specified type [T].
   *
   * @param T The expected type of the message content.
   * @return The raw content of the message, cast to the specified type [T].
   * @throws IllegalArgumentException if the message is neither a [SingleMessage] nor a [MultipleMessages].
   * @since 0.1.0
   */
  fun <T> raw(): T {
    val message: Message<*> = this.original()

    if (message is SingleMessage) {
      @Suppress("UNCHECKED_CAST")
      return message.content as T
    }

    if (message is MultipleMessages) {
      @Suppress("UNCHECKED_CAST")
      return message.content as T
    }

    error("Message is neither a SingleMessage nor a MultipleMessages.")
  }

  /**
   * Prepares the messages for sending to the performers using chat.
   *
   * This method returns a [ToChat] object that can be used to send the messages to the performers.
   * The `addPrefix` parameter allows customization of whether a prefix should be added to the message,
   * with the default behavior controlled by [GeneralConfiguration.USE_PREFIX].
   *
   * @param addPrefix A boolean flag indicating whether to add a prefix to the message (default is [GeneralConfiguration.USE_PREFIX]).
   * @return A [ToChat] object with the prepared messages and performers.
   * @since 0.1.0
   */
  fun useChat(
    addPrefix: Boolean = GeneralConfiguration.USE_PREFIX
  ): ToChat {
    return ToChat(this.performers, this.messages, addPrefix)
  }
}