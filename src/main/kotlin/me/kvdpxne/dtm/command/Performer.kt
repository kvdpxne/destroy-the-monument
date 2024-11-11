package me.kvdpxne.dtm.command

import me.kvdpxne.dtm.shared.Communicative
import me.kvdpxne.dtm.translation.MessageFormatterChains
import me.kvdpxne.dtm.translation.TranslatableCommunicative
import me.kvdpxne.dtm.translation.TranslationService
import me.kvdpxne.dtm.translation.message.MessageKey

interface Performer : Communicative, TranslatableCommunicative {

  val name: String

  fun hasPermission(permission: String): Boolean

  /**
   * @param key
   *
   * @since 0.1.0
   */
  override fun prepareMessage(
    key: MessageKey
  ): MessageFormatterChains {
    return TranslationService.chains()
      .receiver(this)
      .message(key)
  }
}