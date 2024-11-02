package me.kvdpxne.dtm.translation.message

/**
 * Interface representing a provider that supplies a [MessageKey].
 *
 * @since 0.1.0
 */
interface MessageKeyProvider {

  /**
   * Property that holds the [MessageKey] associated with the implementing
   * class.
   *
   * @since 0.1.0
   */
  val messageKey: MessageKey
}