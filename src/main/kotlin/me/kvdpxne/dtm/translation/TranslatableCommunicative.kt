package me.kvdpxne.dtm.translation

import me.kvdpxne.dtm.translation.message.MessageBuilder
import me.kvdpxne.dtm.translation.message.MessageKeys

interface TranslatableCommunicative {

  fun constructMessage(messageKey: MessageKeys): MessageBuilder
}