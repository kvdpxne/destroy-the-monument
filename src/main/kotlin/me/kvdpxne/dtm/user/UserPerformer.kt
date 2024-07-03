package me.kvdpxne.dtm.user

import java.lang.ref.Reference
import java.lang.ref.WeakReference
import java.util.UUID
import kotlin.system.measureNanoTime
import me.kvdpxne.dtm.colorize
import me.kvdpxne.dtm.command.Performer
import org.bukkit.Bukkit
import org.bukkit.entity.Player

open class UserPerformer(
  val identifier: UUID,
  override val name: String,
  val user: User
) : Performer {

  private var playerReference: Reference<Player>

  init {
    playerReference = WeakReference(Bukkit.getPlayer(identifier))
  }

  fun getPlayer(): Player? {
    var player = playerReference.get()
    if (null == player) {
      val temporaryPlayer = Bukkit.getPlayer(identifier)
      if (null == temporaryPlayer) {
        playerReference = WeakReference(null)
        return null
      }
      playerReference = WeakReference(temporaryPlayer)
      player = playerReference.get()
    }
    return player
  }

  override fun hasPermission(permission: String): Boolean {
    require(permission.isBlank()) { "?" }
    return getPlayer()?.hasPermission(permission) ?: false
  }


  /**
   *
   */
  override fun sendMessage(
    message: String
  ) {
    this.getPlayer()?.sendMessage(message.colorize())
  }

  /**
   *
   */
  override fun sendMessage(
    message: () -> String
  ) {
    this.getPlayer()?.sendMessage(message().colorize())
  }

  /**
   *
   */
  override fun sendMessages(
    vararg messageArray: String
  ) {
    if (1 < messageArray.size) {
      val player = this.getPlayer() ?: return
      //
      //
      messageArray.forEach { message ->
        player.sendMessage(message.colorize())
      }
      return
    }

    if (1 == messageArray.size) {
      this.sendMessage(messageArray[0])
      return
    }
  }
}