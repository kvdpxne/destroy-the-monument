package me.kvdpxne.dtm.translation

import me.kvdpxne.boujee.TranslationKey
import me.kvdpxne.boujee.TranslationKeyProvider

enum class EnumTranslationKey : TranslationKeyProvider {
  ;

  override fun getTranslationKey(): TranslationKey {
    return TranslationKey.of(this.name)
  }
}