package me.kvdpxne.dtm.listeners.packet

import com.comphenix.protocol.PacketType
import com.comphenix.protocol.events.PacketAdapter
import com.comphenix.protocol.events.PacketContainer
import com.comphenix.protocol.events.PacketEvent
import java.util.Locale
import me.kvdpxne.dtm.DestroyTheMonument
import me.kvdpxne.dtm.shared.player.localUser
import me.kvdpxne.dtm.translation.locale.Locales
import me.kvdpxne.dtm.translation.TranslationService

object PacketPlayInSettingsListener : PacketAdapter(
  DestroyTheMonument.instance,
  PacketType.Play.Client.SETTINGS
) {

  override fun onPacketReceiving(event: PacketEvent) {
    // net.minecraft.server.v[RELEASE].PacketPlayInSettings
    val packet: PacketContainer = event.packet

    val textLocale: String? = packet.strings.readSafely(0)
    if (textLocale.isNullOrBlank()) {
      return
    }

    val locale: Locale = try {
      Locales.fromString(textLocale)
    } catch (exception: Throwable) {
      TranslationService.defaultLocale
    }

    event.player.localUser.updateLocale(locale)
  }
}