package me.kvdpxne.dtm.translation

import me.kvdpxne.dtm.translation.message.EnumMessageKey
import me.kvdpxne.dtm.translation.message.MessageKey
import me.kvdpxne.dtm.translation.sender.SendChoices

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

  /**
   * @since 0.1.0
   */
  fun throwMessage(
    key: MessageKey,
    func: MessageFormatterChains.() -> SendChoices
  ): Nothing {
    throw Fsss(func(this.prepareMessage(key)).original())
  }

  /**
   * @since 0.1.0
   */
  fun throwMessage(
    key: EnumMessageKey,
    func: MessageFormatterChains.() -> SendChoices
  ): Nothing {
    this.throwMessage(key.messageKey, func)
  }
}