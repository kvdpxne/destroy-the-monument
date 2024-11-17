package me.kvdpxne.dtm.translation.sender

import java.util.Locale
import me.kvdpxne.dtm.Constants
import me.kvdpxne.dtm.command.Performer
import me.kvdpxne.dtm.translation.message.Message
import me.kvdpxne.dtm.translation.message.MultipleMessages
import me.kvdpxne.dtm.translation.message.SingleMessage

/**
 * A message sender that sends messages to performers, optionally with a prefix.
 *
 * @param receivers List of performers who will receive the message.
 * @param messages Map of locale-specific messages to send.
 * @param addPrefix Whether to add a prefix to the message.
 *
 * @since 0.1.0
 */
open class ToChat internal constructor(
  // @formatter:off
                receivers: MutableCollection<Performer>,
                messages : MutableMap<Locale, Message<*>>,
  protected val addPrefix: Boolean
  // @formatter:on
) : AbstractSendable(receivers, messages) {

  companion object {

    /**
     * Pre-formatted message prefix.
     *
     * @since 0.1.0
     */
    private val FORMATTED_PREFIX: String by lazy {
      "&6&l${Constants.NAME} &8>&r"
    }
  }

  protected fun constructMessage(rawMessage: String): String {
    if (this.addPrefix) {
      return "$FORMATTED_PREFIX $rawMessage"
    }

    return rawMessage
  }

  /**
   * Sends a single or multi-line message to a performer, with an optional prefix.
   *
   * @since 0.1.0
   */
  private fun send(
    performer: Performer,
    rawMessage: String,
    addPrefix: Boolean
  ) {
    if (!addPrefix) {
      performer.sendMessage(rawMessage)
      return
    }

    performer.sendMessage("$FORMATTED_PREFIX $rawMessage")
  }

  /**
   * @since 0.1.0
   */
  private fun send(
    performer: Performer,
    locale: Locale,
    addPrefix: Boolean
  ) {
    val message: Message<*> = super.findMessage(locale)

    if (message is SingleMessage) {
      this.send(performer, message.content, addPrefix)
      return
    }

    if (message is MultipleMessages) {
      for (contentLine: String in message.content) {
        this.send(performer, contentLine, addPrefix)
      }
      return
    }

    error("Unsupported message type: ${message::class.simpleName}")
  }

  /**
   * Sends the messages to all performers in the receivers list, then clears
   * the message list.
   *
   * @since 0.1.0
   */
  override fun send() {
    super.iterate { receiver, locale ->
      this.send(receiver, locale, this.addPrefix)
    }
  }
}