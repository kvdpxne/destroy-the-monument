package me.kvdpxne.dtm.translation.sender

import java.util.Locale
import me.kvdpxne.dtm.command.Performer
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
  fun <T> raw(): T {
    require(1 == this.performers.size && 1 == this.messages.size) {
      ""
    }

    val locale: Locale = this.performers.first().locale
    val message: Message<*> = this.messages[locale]!!

    @Suppress("UNCHECKED_CAST", "IMPLICIT_CAST_TO_ANY")
    return when (message) {
      is SingleMessage -> message.content
      is MultipleMessages -> message.content
      else -> error(
        ""
      )
    } as T
  }

  /**
   * @since 0.1.0
   */
  fun useChat(
    addPrefix: Boolean = false
  ): ToChat {
    return ToChat(this.performers, this.messages, addPrefix)
  }
}