package me.kvdpxne.dtm.translation.communitation

import java.util.Locale
import me.kvdpxne.dtm.shared.Tick
import me.kvdpxne.dtm.shared.collections.toPair
import me.kvdpxne.dtm.shared.player.send
import me.kvdpxne.dtm.translation.message.Message
import me.kvdpxne.dtm.translation.message.MultipleMessages
import me.kvdpxne.dtm.translation.message.SingleMessage
import me.kvdpxne.dtm.translation.receiver.Receiver
import me.kvdpxne.dtm.user.LocalUserPerformer

open class ToTitle internal constructor(
  // @formatter:off
              receivers: MutableCollection<Receiver>,
              messages : MutableMap<Locale, Message<*>>,
  private val fadeIn   : Tick,
  private val stay     : Tick,
  private val fadeOut  : Tick,
  private val where    : Byte
  // @formatter:on
) : AbstractSendable(receivers, messages) {

  /**
   * Sends a single or multi-line message to a performer, with an optional prefix.
   *
   * @since 0.1.0
   */
  private fun send(
    receiver: Receiver,
    title: String,
    subTitle: String? = null,
  ) {
    if (receiver !is LocalUserPerformer) {
      return
    }

    receiver.player?.send(
      this.fadeIn,
      this.stay,
      this.fadeOut,
      title,
      subTitle
    )
  }

  /**
   * @since 0.1.0
   */
  private fun send(
    receiver: Receiver,
    locale: Locale
  ) {
    val message: Message<*> = super.findMessage(locale)

    if (message is SingleMessage) {
      if (SendChoices.Where.BOTH == this.where) {
        error(
          "A single message cannot be displayed on both title and sub title."
        )
      }

      when (this.where) {
        SendChoices.Where.TITLE -> this.send(receiver, message.content)
        SendChoices.Where.SUB_TITLE -> this.send(receiver, "", message.content)
      }

      return
    }

    if (message is MultipleMessages) {
      if (SendChoices.Where.BOTH != this.where || 2 != message.content.size) {
        error("")
      }

      val content: Pair<String, String> = message.content.toPair()
      this.send(receiver, content.first, content.second)

      return
    }

    error("Unsupported message type: ${message::class.simpleName}")
  }

  override fun send() {
    super.iterate { receiver, locale ->
      this.send(receiver, locale)
    }
  }
}