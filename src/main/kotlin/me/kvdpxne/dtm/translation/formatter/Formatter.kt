package me.kvdpxne.dtm.translation.formatter

import java.util.Locale
import me.kvdpxne.dtm.translation.TranslationService
import me.kvdpxne.dtm.translation.BasicTranslationKey
import me.kvdpxne.dtm.translation.TranslationKey

class Formatter private constructor(
  val replaceable: MutableMap<String, String>
) {

  companion object {

    fun begin(initialCapacity: Int = 6): Formatter {
      return Formatter(HashMap(initialCapacity))
    }
  }

  fun with(field: String, value: Any): Formatter {
    this.replaceable["{$field}"] = value.toString()
    return this
  }

  fun withTranslated(field: String, locale: Locale, key: TranslationKey): Formatter {
    return this.with(
      field,
      TranslationService
        .findLocalMessagesOrDefault(locale)
        .findRawMessage(key)
    )
  }
}