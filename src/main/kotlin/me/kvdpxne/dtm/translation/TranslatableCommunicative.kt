package me.kvdpxne.dtm.translation

import me.kvdpxne.dtm.translation.message.EnumMessageKey

interface TranslatableCommunicative {

  fun constructMessage(messageKey: EnumMessageKey): MessageFormatterChains
}