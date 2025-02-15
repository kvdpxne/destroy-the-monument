package me.kvdpxne.dtm.translation.locale

import me.kvdpxne.dtm.translation.message.EnumTranslationKey
import me.kvdpxne.dtm.translation.message.Message

/**
 * @since 0.1.0
 */
fun LocaleMessages.findMessageOrNull(
  key: EnumTranslationKey
): Message<*>? {
  return this.findMessageOrNull(key.messageKey)
}

/**
 * @since 0.1.0
 */
fun LocaleMessages.findMessage(
  key: EnumTranslationKey
): Message<*> {
  return this.findMessage(key.messageKey)
}

/**
 * @since 0.1.0
 */
fun <T> LocaleMessages.findRawMessageOrNull(
  key: EnumTranslationKey
): T? {
  return this.findRawMessageOrNull(key.messageKey)
}

/**
 * @since 0.1.0
 */
fun <T> LocaleMessages.findRawMessage(
  key: EnumTranslationKey
): T {
  return this.findRawMessage(key.messageKey)
}