package me.kvdpxne.dtm.translation.sender

import java.util.Locale
import me.kvdpxne.dtm.command.Performer
import me.kvdpxne.dtm.configuration.GeneralConfiguration
import me.kvdpxne.dtm.translation.locale
import me.kvdpxne.dtm.translation.message.Message
import me.kvdpxne.dtm.translation.message.MultipleMessages
import me.kvdpxne.dtm.translation.message.SingleMessage

/**
 * @since 0.1.0
 */
class SendChoices(
  // @formatter:off
  private val performers: MutableCollection<Performer>,
  private val messages  : MutableMap<Locale, Message<*>>
  // @formatter:on
) {

  /**
   * @since 0.1.0
   */
  fun original(): Message<*> {
    require(1 == this.performers.size && 1 == this.messages.size) {
      ""
    }

    val locale: Locale = this.performers.first().locale
    val message: Message<*> = this.messages[locale]!!

    return message
  }

  /**
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

    error("")
  }

  /**
   * @since 0.1.0
   */
  fun useChat(
    addPrefix: Boolean = GeneralConfiguration.USE_PREFIX
  ): ToChat {
    return ToChat(this.performers, this.messages, addPrefix)
  }
}