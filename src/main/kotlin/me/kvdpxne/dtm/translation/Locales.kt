package me.kvdpxne.dtm.translation

import java.util.Locale

/**
 * Utility object for handling locale-related operations.
 *
 * Provides functionality to convert string representations of locales
 * (e.g., "en_US" or "en-US") to [Locale] objects.
 *
 * @since 0.1.0
 */
object Locales {

  /**
   * Parses a locale string and returns a corresponding [Locale] object.
   *
   * The input string should be in the format "language_region" or
   * "language-region", where "language" is a two-letter ISO-639 language
   * code (e.g., "en") and "region" is a two-letter ISO-3166 country/region
   * code (e.g., "US").
   *
   * @param content The locale string to parse. Expected format:
   *               "language_region" or "language-region".
   * @return A [Locale] object constructed based on the parsed language
   *         and region.
   * @throws IllegalArgumentException if the input string is blank or does not
   *         contain both language and region codes.
   *
   * @since 0.1.0
   */
  fun fromString(
    content: String
  ): Locale {
    require(content.isNotBlank()) {
      "The content of the locale cannot be blank."
    }

    val parts: List<String> = content.trim().split("[_-]".toRegex())
    require(2 <= parts.size) {
      "The locale string must contain both a language and a region."
    }

    val language: String = parts[0]
    val region: String = parts[1]

    return Locale.Builder()
      .setLanguage(language)
      .setRegion(region)
      .build()
  }
}