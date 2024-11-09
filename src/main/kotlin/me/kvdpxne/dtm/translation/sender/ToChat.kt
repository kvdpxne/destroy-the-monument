package me.kvdpxne.dtm.translation.sender

import java.util.Locale
import me.kvdpxne.dtm.Constants
import me.kvdpxne.dtm.command.Performer
import me.kvdpxne.dtm.configuration.GeneralConfiguration
import me.kvdpxne.dtm.translation.message.Message
import me.kvdpxne.dtm.translation.message.MultipleMessages
import me.kvdpxne.dtm.translation.message.SingleMessage
import me.kvdpxne.dtm.user.LocalUserPerformer

/**
 * @param receivers
 * @param messages
 * @param addPrefix
 *
 * @since 0.1.0
 */
class ToChat(
  // @formatter:off
              receivers: Collection<Performer>,
              messages : Map<Locale, Message<*>>,
  private val addPrefix: Boolean
  // @formatter:on
) : AbstractSendable(receivers, messages) {

  companion object {

    /**
     * @since 0.1.0
     */
    private val FORMATTED_PREFIX: String by lazy {
      "&6&l${Constants.NAME} &8>&r"
    }

    /**
     * @since 0.1.0
     */
    private fun send(
      performer: Performer,
      rawMessage: String,
      addPrefix: Boolean
    ) {
      if (!addPrefix) {
        performer.sendMessage(rawMessage)
        return
      }

      performer.sendMessage("$FORMATTED_PREFIX $rawMessage")
    }
  }

  override fun send() {
    val shouldAddPrefix: Boolean = GeneralConfiguration.USE_PREFIX
      && this.addPrefix

    for (performer: Performer in this.receivers) {
      if (performer !is LocalUserPerformer) {
        continue
      }

      val locale: Locale = performer.locale
      val message: Message<*> = this.messages[locale]
        ?: throw IllegalStateException("No message found for locale $locale.")

      if (message.isArray) {
        val content: Array<String> = (message as MultipleMessages).content
        for (contentLine: String in content) {
          send(performer, contentLine, shouldAddPrefix)
        }
        return
      }

      val content: String = (message as SingleMessage).content
      send(performer, content, shouldAddPrefix)
    }
  }
}