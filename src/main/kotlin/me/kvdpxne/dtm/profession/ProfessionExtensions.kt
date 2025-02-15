package me.kvdpxne.dtm.profession

import java.util.Locale
import me.kvdpxne.dtm.translation.TranslationService
import me.kvdpxne.dtm.translation.BasicTranslationKey

/**
 * @since 0.1.0
 */
val Profession.translatableMessageKey: BasicTranslationKey
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