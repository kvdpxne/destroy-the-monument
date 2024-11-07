package me.kvdpxne.dtm.listeners.packet

import com.comphenix.protocol.PacketType
import com.comphenix.protocol.events.PacketAdapter
import com.comphenix.protocol.events.PacketContainer
import com.comphenix.protocol.events.PacketEvent
import me.kvdpxne.dtm.DestroyTheMonument
import me.kvdpxne.dtm.listeners.ProjectileLaunchListener

/**
 * @since 0.1.0
 */
object PacketPlayOutEntityDestroyListener : PacketAdapter(
  DestroyTheMonument.instance,
  PacketType.Play.Server.ENTITY_DESTROY
) {

  /**
   * @since 0.1.0
   */
  override fun onPacketSending(
    event: PacketEvent
  ) {
    // net.minecraft.server.v1_8_R3.PacketPlayOutEntityDestroy
    val packet: PacketContainer = event.packet

    val entities: IntArray = packet.integerArrays.read(0)

    for (identifier: Int in entities) {
      for (projectile: Int in ProjectileLaunchListener.projectiles) {
        if (identifier == projectile) {
          ProjectileLaunchListener.projectiles.remove(identifier)
        }
      }
    }
  }
}