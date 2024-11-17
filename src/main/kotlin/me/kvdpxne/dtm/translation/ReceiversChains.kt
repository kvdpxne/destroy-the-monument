package me.kvdpxne.dtm.translation

import me.kvdpxne.dtm.command.Performer

/**
 * Utility class for creating message chains with receivers.
 *
 * This class provides methods to construct [MessageChains] objects with a
 * specified set of receivers. A receiver is represented by the [Performer]
 * interface, which could include players, the console, or other command
 * execution entities.
 *
 * @since 0.1.0
 */
class ReceiversChains internal constructor() {

  /**
   * Constructs a [MessageChains] object for a collection of receivers.
   *
   * The provided [receivers] are added to a mutable list, which is used to
   * initialize the [MessageChains]. This method is suitable for processing
   * iterable collections of performers.
   *
   * @param receivers An [Iterable] collection of [Performer] objects to
   * include in the chain.
   * @return A new [MessageChains] object containing the specified receivers.
   * @since 0.1.0
   */
  fun receivers(
    receivers: Iterable<Performer>
  ): MessageChains {
    return MessageChains(receivers.toMutableList())
  }

  /**
   * Constructs a [MessageChains] object for an array of receivers.
   *
   * The provided [receivers] are converted into a mutable list, which is used
   * to initialize the [MessageChains]. This method is suitable for handling
   * arrays of performers.
   *
   * @param receivers An array of [Performer] objects to include in the chain.
   * @return A new [MessageChains] object containing the specified receivers.
   * @since 0.1.0
   */
  fun receivers(
    receivers: Array<Performer>
  ): MessageChains {
    return MessageChains(receivers.toMutableList())
  }

  /**
   * Constructs a [MessageChains] object for a single receiver.
   *
   * This method creates a chain containing only the specified [performer].
   *
   * @param performer A single [Performer] to include in the chain.
   * @return A new [MessageChains] object containing the specified performer.
   * @since 0.1.0
   */
  fun receiver(
    performer: Performer
  ): MessageChains {
    return MessageChains(mutableListOf(performer))
  }
}