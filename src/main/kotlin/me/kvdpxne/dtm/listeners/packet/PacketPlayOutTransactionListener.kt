package me.kvdpxne.dtm.listeners.packet

import com.comphenix.protocol.PacketType
import com.comphenix.protocol.events.PacketAdapter
import com.comphenix.protocol.events.PacketEvent
import me.kvdpxne.dtm.DestroyTheMonument
import me.kvdpxne.dtm.container.InternalContainerManager
import me.kvdpxne.dtm.shared.event.cancel

object PacketPlayOutTransactionListener : PacketAdapter(
  DestroyTheMonument.instance,
  PacketType.Play.Server.TRANSACTION
) {

  override fun onPacketSending(
    event: PacketEvent
  ) {
    if (InternalContainerManager.hasContainer(event.player.uniqueId)) {
      event.cancel()
    }
  }
}