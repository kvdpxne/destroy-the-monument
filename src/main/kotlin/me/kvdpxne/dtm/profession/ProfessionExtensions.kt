package me.kvdpxne.dtm.profession

import java.util.Locale
import me.kvdpxne.dtm.translation.TranslationService
import me.kvdpxne.dtm.translation.BasicTranslationKey
import me.kvdpxne.dtm.translation.TranslationKey

/**
 * @since 0.1.0
 */
val Profession.translatableMessageKey: TranslationKey
  get() = BasicTranslationKey.of("PROFESSION_${this.name}")


/**
 * @since 0.1.0
 */
fun Profession.translateName(
  locale: Locale
): String {
  return TranslationService
    .findLocalMessagesOrDefault(locale)
    .findRawMessage(this.translatableMessageKey)
}