package me.kvdpxne.dtm.listeners.packet

import com.comphenix.protocol.PacketType
import com.comphenix.protocol.events.PacketAdapter
import com.comphenix.protocol.events.PacketContainer
import com.comphenix.protocol.events.PacketEvent
import me.kvdpxne.dtm.DestroyTheMonument
import me.kvdpxne.dtm.listeners.entity.ProjectileLaunchListener

/**
 * Listens for packets that destroy entities on the server side, and removes
 * projectiles that are marked for removal from the active projectile list.
 *
 * @since 0.1.0
 */
object PacketPlayOutEntityDestroyListener : PacketAdapter(
  DestroyTheMonument.instance,
  PacketType.Play.Server.ENTITY_DESTROY
) {

  /**
   * Triggered when a packet is being sent to destroy entities on the
   * server side. Checks if any of the entities to be destroyed are tracked
   * projectiles, and removes them from the projectile list if found.
   *
   * @param event The packet event representing the entity destruction packet.
   * @since 0.1.0
   */
  override fun onPacketSending(
    event: PacketEvent
  ) {
    // Obtain the PacketContainer for the entity destroy packet.
    val packet: PacketContainer = event.packet

    // Retrieve the array of entity identifiers targeted for destruction.
    val entities: IntArray = packet.integerArrays.read(0)

    for (entity: Int in entities) {
      val iterator: MutableIterator<Int> = ProjectileLaunchListener.projectiles.iterator()
      while (iterator.hasNext()) {
        if (entity == iterator.next()) {
          iterator.remove()
        }
      }
    }
  }
}