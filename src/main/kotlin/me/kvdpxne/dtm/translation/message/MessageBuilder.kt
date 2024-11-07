package me.kvdpxne.dtm.translation.message

import java.util.Locale
import me.kvdpxne.dtm.Constants
import me.kvdpxne.dtm.command.Performer
import me.kvdpxne.dtm.configuration.GeneralConfiguration
import me.kvdpxne.dtm.shared.text.colorize
import me.kvdpxne.dtm.translation.TranslationService
import me.kvdpxne.dtm.translation.formatter.Formatter
import me.kvdpxne.dtm.user.LocalUserPerformer

class MessageBuilder {

  private val receivers: MutableList<Performer> = mutableListOf()
  private val messages: MutableMap<Locale, String> = mutableMapOf()

  /**
   * @since 0.1.0
   */
  fun receivers(receivers: Iterable<Performer>): MessageBuilder {
    this.receivers.addAll(receivers)
    return this
  }

  /**
   * @since 0.1.0
   */
  fun receiver(performer: Performer): MessageBuilder {
    this.receivers.add(performer)
    return this
  }

  /**
   * @since 0.1.0
   */
  fun message(key: MessageKeys): MessageBuilder {
    for (receiver in this.receivers) {
      if (receiver is LocalUserPerformer) {
        val message: String = TranslationService
          .findLocalMessages(receiver.locale)
          .findMessage(key.messageKey)

        this.messages[receiver.locale] = message
      }
    }
    return this
  }

  /**
   * @since 0.1.0
   */
  fun formatter(formatter: Formatter): MessageBuilder {
    for ((field: String, value: String) in formatter.replaceable) {
      for ((locale: Locale, message: String) in this.messages) {
        if (!message.contains(field)) {
          continue
        }

        val newMessage = message.replace(field, value)
        this.messages[locale] = newMessage
      }
    }
    return this
  }

  /**
   * @since 0.1.0
   */
  fun send() {
    for (receiver: Performer in this.receivers) {
      if (receiver !is LocalUserPerformer) {
        continue
      }

      val locale: Locale = receiver.locale
      val message: String = this.messages[locale] ?: continue

      val formattedMessage: String = if (GeneralConfiguration.USE_PREFIX) {
        "&6&l${Constants.NAME} &8>&r $message"
      } else {
        message
      }.colorize

      receiver.sendMessage(formattedMessage)
    }
  }
}