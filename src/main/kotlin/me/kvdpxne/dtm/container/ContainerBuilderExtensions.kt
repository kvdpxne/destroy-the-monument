package me.kvdpxne.dtm.container

import java.util.Locale
import me.kvdpxne.dtm.translation.TranslationService
import me.kvdpxne.dtm.translation.locale.findRawMessage
import me.kvdpxne.dtm.translation.message.EnumMessageKey

/**
 * @param locale
 * @param key
 *
 * @since 0.1.0
 */
fun <T, U : ContainerOpener<T>> ContainerBuilder<T, U>.displayName(
  locale: Locale,
  key: EnumMessageKey
): ContainerBuilder<T, U> {
  return this.displayName(
    TranslationService
      .findLocalMessagesOrDefault(locale)
      .findRawMessage(key)
  )
}