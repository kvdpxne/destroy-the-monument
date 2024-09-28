package me.kvdpxne.dtm.listeners.netty

import net.minecraft.server.v1_7_R4.NetworkManager
import net.minecraft.server.v1_7_R4.PacketPlayInBlockDig
import net.minecraft.server.v1_7_R4.PacketPlayInFlying
import net.minecraft.util.io.netty.channel.Channel
import net.minecraft.util.io.netty.channel.ChannelDuplexHandler
import net.minecraft.util.io.netty.channel.ChannelHandlerContext
import net.minecraft.util.io.netty.channel.ChannelPromise
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer
import org.bukkit.entity.Player

val NetworkManager.channel: Channel
  get() {
  val channelField = NetworkManager::class.java.getDeclaredField("m")
  channelField.isAccessible = true

  val channel = channelField.get(this) as Channel
  channelField.isAccessible = false

  return channel
}


object TeammateActiveProfessionAbilityListener {

  private fun removePlayer(player: Player) {
    val channel = (player as CraftPlayer).handle.playerConnection.networkManager.channel

    channel.eventLoop().submit {
      channel.pipeline().remove(player.getName())
    }
  }

  fun addPlayer(player: Player) {
    val channelDuplexHandler = object : ChannelDuplexHandler() {

      override fun channelRead(
        channelHandlerContext: ChannelHandlerContext,
        packet: Any
      ) {
        if (packet is PacketPlayInBlockDig) {
          PacketPlayInBlockDigListener.handlePacketPlayInBlockDig(packet, player)
          super.channelRead(channelHandlerContext, packet)
          return
        }

        if (packet !is PacketPlayInFlying) {
          println("read: $packet")
        }


        super.channelRead(channelHandlerContext, packet)
      }

      override fun write(
        channelHandlerContext: ChannelHandlerContext,
        packet: Any,
        channelPromise: ChannelPromise
      ) {
//        println("write: $packet")
        super.write(channelHandlerContext, packet, channelPromise)
      }
    }

    val pipeline = (player as CraftPlayer).handle.playerConnection.networkManager.channel.pipeline()
    pipeline.addBefore("packet_handler", player.getName(), channelDuplexHandler)
  }
}