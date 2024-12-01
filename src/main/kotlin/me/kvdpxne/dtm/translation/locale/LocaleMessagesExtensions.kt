package me.kvdpxne.dtm.translation.locale

import me.kvdpxne.dtm.translation.message.EnumMessageKey
import me.kvdpxne.dtm.translation.message.Message

/**
 * @since 0.1.0
 */
fun LocaleMessages.findMessageOrNull(
  key: EnumMessageKey
): Message<*>? {
  return this.findMessageOrNull(key.messageKey)
}

/**
 * @since 0.1.0
 */
fun LocaleMessages.findMessage(
  key: EnumMessageKey
): Message<*> {
  return this.findMessage(key.messageKey)
}

/**
 * @since 0.1.0
 */
fun <T> LocaleMessages.findRawMessageOrNull(
  key: EnumMessageKey
): T? {
  return this.findRawMessageOrNull(key.messageKey)
}

/**
 * @since 0.1.0
 */
fun <T> LocaleMessages.findRawMessage(
  key: EnumMessageKey
): T {
  return this.findRawMessage(key.messageKey)
}