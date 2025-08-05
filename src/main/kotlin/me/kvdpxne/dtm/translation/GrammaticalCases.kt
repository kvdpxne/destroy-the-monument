package me.kvdpxne.dtm.translation

/**
 * Provides standardized grammatical case definitions for multilingual text
 * formatting.
 *
 * Used to handle language-specific word forms in translations (e.g., different
 * noun endings in Polish). Each case represents how words change based on their
 * role in a sentence.
 *
 * @since 0.1.0
 */
object GrammaticalCases {

  /**
   * Represents the base/naming form of a word (e.g., "the player").
   * Used when the word is the subject of a sentence.
   *
   * @since 0.1.0
   */
  const val NOMINATIVE: Byte = (1 shl 0).toByte()

  /**
   * Represents the possession form (e.g., "of the player").
   * Used to show ownership or relationships.
   *
   * @since 0.1.0
   */
  const val GENITIVE: Byte = (1 shl 1).toByte()

  /**
   * Represents the indirect object form (e.g., "to the player").
   * Used for recipients or targets.
   *
   * @since 0.1.0
   */
  const val DATIVE: Byte = (1 shl 2).toByte()

  /**
   * Represents the direct object form (e.g., "see the player").
   * Used for the primary object of an action.
   *
   * @since 0.1.0
   */
  const val ACCUSATIVE: Byte = (1 shl 3).toByte()

  /**
   * Represents the instrument form (e.g., "with the player").
   * Used to indicate tools or companions.
   *
   * @since 0.1.0
   */
  const val INSTRUMENTAL: Byte = (1 shl 4).toByte()

  /**
   * Represents the location form (e.g., "at the player").
   * Used for positional contexts.
   *
   * @since 0.1.0
   */
  const val LOCATIVE: Byte = (1 shl 5).toByte()

  /**
   * Maps case bit flags to human-readable names for translation system usage.
   * Enables proper word form selection in different languages.
   *
   * @since 0.1.0
   */
  private val flagToCaseName: Map<Byte, String> = hashMapOf(
    this.NOMINATIVE to "nominative",
    this.GENITIVE to "genitive",
    this.DATIVE to "dative",
    this.ACCUSATIVE to "accusative",
    this.INSTRUMENTAL to "instrumental",
    this.LOCATIVE to "locative"
  )

  /**
   * Converts a grammatical case bit flag to its corresponding name.
   * Returns 'genitive' as default for unrecognized flags to ensure safe
   * translations.
   *
   * @param flag Bit flag representing a grammatical case
   * @return Human-readable case name for translation keys
   * @since 0.1.0
   */
  fun toKeyName(flag: Byte): String {
    return this.flagToCaseName[flag] ?: "genitive"
  }
}