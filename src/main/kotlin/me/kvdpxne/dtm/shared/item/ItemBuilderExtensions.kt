package me.kvdpxne.dtm.shared.item

import java.util.Locale
import me.kvdpxne.dtm.translation.TranslationService
import me.kvdpxne.dtm.translation.formatter.Formatter
import me.kvdpxne.dtm.translation.message.EnumTranslationKey
import me.kvdpxne.dtm.translation.message.Message
import me.kvdpxne.dtm.translation.BasicTranslationKey
import me.kvdpxne.dtm.translation.TranslationKey
import me.kvdpxne.dtm.translation.message.MultipleMessages
import me.kvdpxne.dtm.translation.message.SingleMessage

/**
 * @throws IllegalArgumentException
 * @throws IllegalStateException
 *
 * @since 0.1.0
 */
fun ItemBuilder.displayName(
  locale: Locale,
  key: TranslationKey,
  formatter: (() -> Formatter)? = null
): ItemBuilder {
  var message: Message<*> = TranslationService
    .findLocalMessagesOrDefault(locale)
    .findMessage(key)

  check(message is SingleMessage) {
    "The displayed item name must be a single message."
  }

  if (null != formatter) {
    message = message.format(formatter())
  }

  return this.name(message.content as String)
}

/**
 * @since 0.1.0
 */
fun ItemBuilder.lore(
  locale: Locale,
  key: TranslationKey,
  formatter: (() -> Formatter)? = null
): ItemBuilder {
  var message: Message<*> =
    TranslationService
      .findLocalMessagesOrDefault(locale)
      .findMessage(key)

  if (message is SingleMessage) {
    message = MultipleMessages(listOf(message.content))
  }

  if (null != formatter) {
    message = message.format(formatter())
  }

  @Suppress("UNCHECKED_CAST")
  return this.lore(*(message.content as Collection<String>).toTypedArray())
}


/**
 * @since 0.1.0
 */
fun ItemBuilder.displayName(
  locale: Locale,
  key: EnumTranslationKey,
  formatter: (() -> Formatter)? = null
): ItemBuilder {
  return this.displayName(locale, key.messageKey, formatter)
}

/**
 * @since 0.1.0
 */
fun ItemBuilder.lore(
  locale: Locale,
  key: EnumTranslationKey,
  formatter: (() -> Formatter)? = null
): ItemBuilder {
  return this.lore(locale, key.messageKey, formatter)
}