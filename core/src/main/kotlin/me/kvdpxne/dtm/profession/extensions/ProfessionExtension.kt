package me.kvdpxne.dtm.profession.extensions

import me.kvdpxne.boujee.TranslationKey
import me.kvdpxne.boujee.TranslationKeyProvider
import me.kvdpxne.boujee.locale.LocaleSourceProvider
import me.kvdpxne.dtm.profession.Profession
import me.kvdpxne.dtm.translation.SingletonTranslationService

/**
 * @since 0.1.0
 */
val Profession.nameTranslationKey: TranslationKeyProvider
  get() = TranslationKey.of("PROFESSION_${this.getIdentifier()}")


/**
 * @since 0.1.0
 */
fun Profession.translateName(
  localeSourceProvider: LocaleSourceProvider
): String {
  return SingletonTranslationService
    .findLocaleTranslationsOrDefault(localeSourceProvider)
    .findTextOrNull(this.nameTranslationKey)
    .contentAsString
}