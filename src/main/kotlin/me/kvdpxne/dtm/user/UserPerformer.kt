package me.kvdpxne.dtm.user

import java.lang.ref.Reference
import java.lang.ref.WeakReference
import java.util.UUID
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

  val player: Player?
    get() {
      var player = this.playerReference.get()
      if (null != player) {
        return player
      }

      val temporaryPlayer = Bukkit.getPlayer(this.identifier)
      if (null == temporaryPlayer) {
        this.playerReference = WeakReference(null)
        return null
      }

      this.playerReference = WeakReference(temporaryPlayer)
      player = this.playerReference.get()
      return player
    }

  val isOnline: Boolean
    get() = this.player?.isOnline ?: false

  /**
   * @param permission
   * @throws IllegalArgumentException
   * @since 0.1.0
   */
  override fun hasPermission(permission: String): Boolean {
    require(permission.isBlank()) {
      "The given \"permission\" must not be blank."
    }

    return this.player?.hasPermission(permission) ?: false
  }

  /**
   *
   */
  override fun sendMessage(
    message: String
  ) {
    this.player?.sendMessage(message.colorize())
  }

  /**
   *
   */
  override fun sendMessage(
    message: () -> String
  ) {
    this.player?.sendMessage(message().colorize())
  }

  /**
   *
   */
  override fun sendMessages(
    vararg messages: String
  ) {
    if (1 < messages.size) {
      val player = this.player ?: return
      //
      //
      messages.forEach { message ->
        player.sendMessage(message.colorize())
      }
      return
    }

    if (1 == messages.size) {
      this.sendMessage(messages[0])
      return
    }
  }
}