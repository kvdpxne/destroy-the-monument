package me.kvdpxne.dtm.translation.sender

import java.util.Locale
import me.kvdpxne.dtm.command.Performer
import me.kvdpxne.dtm.translation.message.Message

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
  fun useChat(
    addPrefix: Boolean = false
  ): ToChat {
    return ToChat(this.performers, this.messages, addPrefix)
  }
}