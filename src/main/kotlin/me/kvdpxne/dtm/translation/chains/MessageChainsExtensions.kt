package me.kvdpxne.dtm.translation.chains

import me.kvdpxne.dtm.translation.message.EnumTranslationKey

/**
 * Adds a message for the receivers in the chain, based on the
 * provided [EnumTranslationKey].
 *
 * This method is similar to the other `message` method but is specifically
 * designed to handle messages that are represented by an enum type.
 *
 * @param key The [EnumTranslationKey] that identifies the message to be sent to the receivers.
 * @return A [MessageFormatterChains] object that allows further formatting and message manipulation.
 * @since 0.1.0
 */
fun MessageChains.message(
  key: EnumTranslationKey
): MessageFormatterChains {
  return this.message(key.messageKey)
}