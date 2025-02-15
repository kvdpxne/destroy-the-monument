package me.kvdpxne.dtm.translation.communitation

import me.kvdpxne.dtm.translation.chains.MessageFormatterChains
import me.kvdpxne.dtm.translation.message.EnumTranslationKey
import me.kvdpxne.dtm.translation.message.MessageHolderException
import me.kvdpxne.dtm.translation.BasicTranslationKey
import me.kvdpxne.dtm.translation.TranslationKey

/**
 * @since 0.1.0
 */
interface TranslatableCommunicative {

  /**
   * @since 0.1.0
   */
  fun prepareMessage(
    key: TranslationKey
  ): MessageFormatterChains

  /**
   * @since 0.1.0
   */
  fun prepareMessage(
    key: EnumTranslationKey
  ): MessageFormatterChains {
    return this.prepareMessage(key.messageKey)
  }

  /**
   * @since 0.1.0
   */
  fun throwMessage(
    key: TranslationKey,
    func: MessageFormatterChains.() -> SendChoices
  ): Nothing {
    throw MessageHolderException(func(this.prepareMessage(key)).original())
  }

  /**
   * @since 0.1.0
   */
  fun throwMessage(
    key: EnumTranslationKey,
    func: MessageFormatterChains.() -> SendChoices
  ): Nothing {
    this.throwMessage(key.messageKey, func)
  }
}