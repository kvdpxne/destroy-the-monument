package me.kvdpxne.dtm.translation.communitation

import java.util.Locale
import me.kvdpxne.dtm.translation.message.Message
import me.kvdpxne.dtm.translation.receiver.Receiver

/**
 * @param receivers
 * @param messages
 *
 * @since 0.1.0
 */
abstract class AbstractSendable protected constructor(
  // @formatter:off
  protected val receivers: MutableCollection<Receiver>,
  protected val messages : MutableMap<Locale, Message<*>>
  // @formatter:on
) : Sendable {

  init {
    require(this.receivers.isNotEmpty()) {
      "At least one performer needs to be defined."
    }

    require(this.messages.isNotEmpty()) {
      "At least one message needs to be defined."
    }
  }

  protected fun findMessage(locale: Locale): Message<*> {
    return this.messages[locale]
      ?: error("No message found for locale $locale.")
  }

  protected fun iterate(handle: (Receiver, Locale) -> Unit) {
    val iterator: MutableIterator<Receiver> = this.receivers.iterator()
    while (iterator.hasNext()) {
      val receiver: Receiver = iterator.next()

      handle(receiver, receiver.locale)
      iterator.remove()
    }

    this.messages.clear()
  }
}