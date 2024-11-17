package me.kvdpxne.dtm.translation.sender

import java.util.Locale
import me.kvdpxne.dtm.command.Performer
import me.kvdpxne.dtm.translation.locale
import me.kvdpxne.dtm.translation.message.Message

/**
 * @param receivers
 * @param messages
 *
 * @since 0.1.0
 */
abstract class AbstractSendable protected constructor(
  // @formatter:off
  protected val receivers: MutableCollection<Performer>,
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

  protected fun iterate(handle: (Performer, Locale) -> Unit) {
    val iterator: MutableIterator<Performer> = this.receivers.iterator()
    while (iterator.hasNext()) {
      val receiver: Performer = iterator.next()

      handle(receiver, receiver.locale)
      iterator.remove()
    }

    this.messages.clear()
  }
}