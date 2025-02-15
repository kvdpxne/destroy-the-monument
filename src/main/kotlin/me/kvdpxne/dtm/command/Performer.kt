package me.kvdpxne.dtm.command

import me.kvdpxne.dtm.shared.Communicative
import me.kvdpxne.dtm.translation.chains.MessageFormatterChains
import me.kvdpxne.dtm.translation.communitation.TranslatableCommunicative
import me.kvdpxne.dtm.translation.TranslationService
import me.kvdpxne.dtm.translation.BasicTranslationKey
import me.kvdpxne.dtm.translation.TranslationKey
import me.kvdpxne.dtm.translation.receiver.Receiver

interface Performer : Receiver, Communicative, TranslatableCommunicative {

  val name: String

  fun hasPermission(permission: String): Boolean

  /**
   * @param key
   *
   * @since 0.1.0
   */
  override fun prepareMessage(
    key: TranslationKey
  ): MessageFormatterChains {
    return TranslationService.chains()
      .receiver(this)
      .message(key)
  }
}