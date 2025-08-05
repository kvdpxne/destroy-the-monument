package me.kvdpxne.dtm.translation

import me.kvdpxne.dtm.Providable

/**
 * Interface representing a provider that supplies a [BasicTranslationKey].
 *
 * @since 0.1.0
 */
interface TranslationKeyProvider : Translation, Providable {

  /**
   * Property that holds the [TranslationKey] associated with the implementing
   * class.
   *
   * @since 0.1.0
   */
  val messageKey: TranslationKey
}