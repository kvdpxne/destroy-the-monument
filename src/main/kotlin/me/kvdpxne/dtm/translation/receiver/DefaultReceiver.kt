package me.kvdpxne.dtm.translation.receiver

import java.util.Locale
import me.kvdpxne.dtm.translation.TranslationService

interface DefaultReceiver : Receiver {

  override val locale: Locale
    get() = TranslationService.defaultLocale
}