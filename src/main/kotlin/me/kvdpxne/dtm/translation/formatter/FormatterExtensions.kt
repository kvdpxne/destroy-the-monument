package me.kvdpxne.dtm.translation.formatter

import java.util.Locale
import me.kvdpxne.dtm.translation.message.EnumTranslationKey

fun Formatter.withTranslated(
  field: String,
  locale: Locale,
  key: EnumTranslationKey
): Formatter {
  return this.withTranslated(field, locale, key.messageKey)
}