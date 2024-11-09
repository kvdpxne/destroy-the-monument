package me.kvdpxne.dtm.translation

import me.kvdpxne.dtm.command.Performer

/**
 * @since 0.1.0
 */
class ReceiversChains internal constructor() {

  /**
   * @since 0.1.0
   */
  fun receivers(
    receivers: Iterable<Performer>
  ): MessageChains {
    return MessageChains(receivers.toList())
  }

  /**
   * @since 0.1.0
   */
  fun receivers(
    receivers: Array<Performer>
  ): MessageChains {
    return MessageChains(receivers.toList())
  }

  /**
   * @since 0.1.0
   */
  fun receiver(
    performer: Performer
  ): MessageChains {
    return MessageChains(listOf(performer))
  }
}