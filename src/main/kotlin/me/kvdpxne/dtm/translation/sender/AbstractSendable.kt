package me.kvdpxne.dtm.translation.sender

import java.util.Locale
import me.kvdpxne.dtm.command.Performer
import me.kvdpxne.dtm.translation.message.Message

/**
 * @param receivers
 * @param messages
 *
 * @since 0.1.0
 */
abstract class AbstractSendable protected constructor(
  // @formatter:off
  protected val receivers: Collection<Performer>,
  protected val messages : Map<Locale, Message<*>>
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
}