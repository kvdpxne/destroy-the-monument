package me.kvdpxne.dtm.translation

import me.kvdpxne.dtm.translation.message.EnumMessageKey
import me.kvdpxne.dtm.translation.message.MessageKey

/**
 * @since 0.1.0
 */
interface TranslatableCommunicative {

  /**
   * @since 0.1.0
   */
  fun prepareMessage(
    key: MessageKey
  ): MessageFormatterChains

  /**
   * @since 0.1.0
   */
  fun prepareMessage(
    key: EnumMessageKey
  ): MessageFormatterChains {
    return this.prepareMessage(key.messageKey)
  }
}