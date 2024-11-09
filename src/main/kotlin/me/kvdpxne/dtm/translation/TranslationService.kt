package me.kvdpxne.dtm.translation

import java.util.Locale
import me.kvdpxne.dtm.shared.debug.Debug

/**
 * Service responsible for managing translations by locale.
 * Provides methods to load, retrieve, and manage locale-specific messages.
 *
 * @since 0.1.0
 */
object TranslationService {

  /**
   * @since 0.1.0
   */
  private val receiversChains: ReceiversChains by lazy {
    ReceiversChains()
  }

  private val _localeMessages: MutableMap<Locale, LocaleMessages> = mutableMapOf()
  var defaultLocale: Locale = Locale.US

  /**
   * Provides a list of all loaded locale messages.
   *
   * @since 0.1.0
   */
  val localeMessages: Collection<LocaleMessages>
    get() = this._localeMessages.values.toList()

  /**
   * Returns the number of loaded locale messages.
   *
   * @since 0.1.0
   */
  val size: Int
    get() = this._localeMessages.size

  /**
   * Provides access to the chain of receivers for translations.
   *
   * @since 0.1.0
   */
  fun chains(): ReceiversChains {
    return this.receiversChains
  }

  /**
   * Finds locale-specific messages or returns null if not found.
   *
   * @param locale The locale to retrieve messages for.
   * @since 0.1.0
   */
  fun findLocalMessagesOrNull(locale: Locale): LocaleMessages? {
    return this._localeMessages[locale]
  }

  /**
   * Finds locale-specific messages or throws an exception if not found.
   *
   * @param locale The locale to retrieve messages for.
   * @throws IllegalArgumentException If the specified locale is not found.
   *
   * @since 0.1.0
   */
  fun findLocalMessages(locale: Locale): LocaleMessages {
    return requireNotNull(this.findLocalMessagesOrNull(locale)) {
      "Locale $locale not found."
    }
  }

  /**
   * Finds locale-specific messages, returning the default locale messages if the specified locale is not found.
   *
   * @param locale The locale to retrieve messages for.
   *
   * @since 0.1.0
   */
  fun findLocalMessagesOrDefaultOrNull(locale: Locale): LocaleMessages? {
    return this._localeMessages[locale]
      ?: this._localeMessages[this.defaultLocale]
  }

  /**
   * Finds locale-specific messages or defaults, throwing an exception if both are not found.
   *
   * @param locale The locale to retrieve messages for.
   * @throws IllegalArgumentException If neither the specified locale nor the default locale is found.
   *
   * @since 0.1.0
   */
  fun findLocalMessagesOrDefault(locale: Locale): LocaleMessages {
    return requireNotNull(this.findLocalMessagesOrDefaultOrNull(locale)) {
      "Locale $locale not found."
    }
  }

  /**
   * Loads translations from the source and populates `_localeMessages`.
   *
   * @since 0.1.0
   */
  fun loadTranslations() {
    for (localeMessages: LocaleMessages in InsideJsonReader.read()) {
      this._localeMessages[localeMessages.locale] = localeMessages
    }
  }

  /**
   * Reloads translations for a specific locale.
   *
   * @param locale The locale to reload translations for.
   *
   * @since 0.1.0
   */
  fun reloadTranslation(locale: Locale) {
    val updatedMessages: LocaleMessages = InsideJsonReader.read(locale.toString())
    this._localeMessages[locale] = updatedMessages

    Debug.log {
      "Reloaded translations for locale: $locale"
    }
  }

  /**
   * Reloads all translations from the source.
   *
   * @since 0.1.0
   */
  fun reloadTranslations() {
    this.loadTranslations()

    Debug.log {
      "Reloaded all translations."
    }
  }

  /**
   * Clears all loaded translations from memory.
   *
   * @since 0.1.0
   */
  fun clearTranslations() {
    this._localeMessages.clear()

    Debug.log {
      "Cleared all loaded translations."
    }
  }
}