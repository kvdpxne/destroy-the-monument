package me.kvdpxne.dtm.translation

/**
 * @since 0.1.0
 */
interface TranslationKey : Translation, TranslationKeyProvider {

  /**
   * @since 0.1.0
   */
  val name: String

  /**
   * @since 0.1.0
   */
  val ordinal: Int
}