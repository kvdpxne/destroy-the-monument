package me.kvdpxne.dtm.listeners.packet

import com.comphenix.protocol.PacketType
import com.comphenix.protocol.events.PacketAdapter
import com.comphenix.protocol.events.PacketContainer
import com.comphenix.protocol.events.PacketEvent
import java.util.Locale
import me.kvdpxne.dtm.DestroyTheMonument
import me.kvdpxne.dtm.shared.player.localUser

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

    val parts: List<String> = textLocale.split("_")
    val language: String = parts[0]
    val region: String = parts[1]

    val locale: Locale = Locale.Builder()
      .setLanguage(language)
      .setRegion(region)
      .build()

    event.player.localUser.performer.locale = locale
  }
}