package me.kvdpxne.dtm.translation

import me.kvdpxne.dtm.translation.message.EnumTranslationKey

object TranslationKeyRegistry {

  /**
   * @since 0.1.0
   */
  fun registerTranslationKeys() {
    for (enum: Enum<EnumTranslationKey> in EnumTranslationKey.entries) {
      BasicTranslationKey.of(enum.name)
    }
  }
}