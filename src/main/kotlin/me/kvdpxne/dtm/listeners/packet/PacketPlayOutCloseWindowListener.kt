package me.kvdpxne.dtm.listeners.packet

import com.comphenix.protocol.PacketType
import com.comphenix.protocol.events.PacketAdapter
import com.comphenix.protocol.events.PacketEvent
import me.kvdpxne.dtm.DestroyTheMonument
import me.kvdpxne.dtm.container.InternalContainerManager

object PacketPlayOutCloseWindowListener : PacketAdapter(
  DestroyTheMonument.instance,
  PacketType.Play.Client.CLOSE_WINDOW
) {

  override fun onPacketReceiving(
    event: PacketEvent
  ) {
    //
    InternalContainerManager.removeContainerByIdentifier(
      event.player.uniqueId
    )
  }
}