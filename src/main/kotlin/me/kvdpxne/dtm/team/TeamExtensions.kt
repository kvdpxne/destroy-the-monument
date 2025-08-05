package me.kvdpxne.dtm.team

import java.util.Locale
import me.kvdpxne.dtm.translation.TranslationService
import me.kvdpxne.dtm.translation.BasicTranslationKey
import me.kvdpxne.dtm.translation.GrammaticalCases
import me.kvdpxne.dtm.translation.TranslationKey

/**
 * Gets the translated team name for a specific language and grammatical case.
 * Handles language-specific word forms (like different endings in Polish) based
 * on context. For example, returns "Red Team" in nominative case or
 * "of Red Team" in genitive case.
 *
 * @param locale The language to use for translation (e.g., English, Polish)
 * @param caseFlag Specifies how the word should be inflected (e.g., for possession, location)
 * @return Translated team name appropriate for the language and context
 * @since 0.1.0
 */
fun Team.translateName(
  locale: Locale,
  caseFlag: Byte
): String {
  val caseName: String = GrammaticalCases.toKeyName(caseFlag)
  val key: TranslationKey = BasicTranslationKey.of("TEAM_${this.name}_$caseName")

  return TranslationService
    .findLocalMessagesOrDefault(locale)
    .findRawMessage(key)
}