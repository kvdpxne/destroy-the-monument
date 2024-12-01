package me.kvdpxne.dtm.shared.item

import java.util.Locale
import me.kvdpxne.dtm.translation.TranslationService
import me.kvdpxne.dtm.translation.message.EnumMessageKey

fun ItemBuilder.displayName(
  locale: Locale,
  key: EnumMessageKey
): ItemBuilder {
  return this.name(
    TranslationService
      .findLocalMessagesOrDefault(locale)
      .findRawMessage<String>(key.messageKey)
  )
}