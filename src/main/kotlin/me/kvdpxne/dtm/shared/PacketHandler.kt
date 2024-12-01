package me.kvdpxne.dtm.shared

interface PacketHandler<T> : PlayerProvider<T> {

  fun sendPacket(packet: Any)
}