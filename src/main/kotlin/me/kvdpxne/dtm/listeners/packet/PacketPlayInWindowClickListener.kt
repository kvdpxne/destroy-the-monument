package me.kvdpxne.dtm.listeners.packet

import com.comphenix.protocol.PacketType
import com.comphenix.protocol.ProtocolLibrary
import com.comphenix.protocol.ProtocolManager
import com.comphenix.protocol.events.PacketAdapter
import com.comphenix.protocol.events.PacketContainer
import com.comphenix.protocol.events.PacketEvent
import me.kvdpxne.dtm.DestroyTheMonument
import me.kvdpxne.dtm.container.InternalContainerManager
import me.kvdpxne.dtm.container.SlotHandler
import me.kvdpxne.dtm.container.SlotTypes
import me.kvdpxne.dtm.shared.player.localUser
import me.kvdpxne.dtm.user.performer.LocalUserPerformer
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

object PacketPlayInWindowClickListener : PacketAdapter(
  DestroyTheMonument.instance,
  PacketType.Play.Client.WINDOW_CLICK
) {

  override fun onPacketReceiving(
    event: PacketEvent
  ) {
    // PacketPlayInWindowClick
    val packet: PacketContainer = event.packet

    val index: Int = packet.integers.read(1)
    if (SlotTypes.OUTSIDE == index) {
      return
    }

    //
    val player: Player = event.player

    //
    val container = InternalContainerManager
      .findContainerByIdentifierOrNull<Player>(player.uniqueId)
      ?: return

    // The numeric identifier of the currently open container.
    // Represented by 8-bits with a sign, which takes on unsigned values,
    // usually from 0 to 100 inclusive.
    val identifier: Int = packet.integers.read(0)

    val protocolManager: ProtocolManager = ProtocolLibrary.getProtocolManager()
    val first: PacketContainer = protocolManager.createPacket(PacketType.Play.Server.SET_SLOT)

    // item in cursor
    first.integers.write(0, -1)
    first.integers.write(1, -1)
    protocolManager.sendServerPacket(event.player, first)

    val itemStack: ItemStack? = packet.itemModifier.readSafely(0)
    if (null != itemStack) {

      // item in container
      first.integers.write(0, identifier)
      first.integers.write(1, index)
      first.itemModifier.write(0, itemStack)
      protocolManager.sendServerPacket(event.player, first)
    }

    container.update(event.player.localUser.performer)
    event.player.updateInventory()

    val handler = container.getSlot(index)?.handler as? SlotHandler<LocalUserPerformer>? ?: return
    handler(event.player.localUser.performer)
  }
}