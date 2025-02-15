package me.kvdpxne.dtm.container

import java.util.Locale
import me.kvdpxne.dtm.translation.TranslationService
import me.kvdpxne.dtm.translation.locale.findRawMessage
import me.kvdpxne.dtm.translation.message.EnumTranslationKey

/**
 * @param locale
 * @param key
 *
 * @since 0.1.0
 */
fun <T : ContainerOpener<*>> ContainerBuilder<T>.displayName(
  locale: Locale,
  key: EnumTranslationKey
): ContainerBuilder<T> {
  return this.displayName(
    TranslationService
      .findLocalMessagesOrDefault(locale)
      .findRawMessage(key)
  )
}