package me.kvdpxne.dtm.user

import java.lang.ref.Reference
import java.lang.ref.WeakReference
import java.util.UUID
import me.kvdpxne.dtm.colorize
import me.kvdpxne.dtm.configuration.Configuration
import org.bukkit.Bukkit
import org.bukkit.entity.Player

class LocalUserPerformerImpl(
  // @formatter:off
  override val identifier: UUID,
  override val user      : LocalUser
  // @formatter:on
) : LocalUserPerformer {

  private val _player: Reference<Player> by lazy {
    WeakReference(Bukkit.getPlayer(this.identifier))
  }

  override val name: String
    get() = this.user.name

  override val player: Player?
    get() = this._player.get()

  override val isOnline: Boolean
    get() = this.player?.isOnline ?: false

  override val isOperator: Boolean
    get() = this.player?.isOp ?: false

  override fun hasPermission(
    permission: String
  ): Boolean {
    require(permission.isNotBlank()) {
      "The given \"permission\" must not be blank."
    }

    //
    if (Configuration.OP_F && this.isOperator) {
      return true
    }

    //
    return this.player?.hasPermission(permission) ?: false
  }

  /**
   *
   */
  override fun sendMessage(
    message: String
  ) {
    this.player?.sendMessage(message.colorize)
  }

  /**
   *
   */
  override fun sendMessage(
    message: () -> String
  ) {
    this.player?.sendMessage(message().colorize)
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
        player.sendMessage(message.colorize)
      }
      return
    }

    if (1 == messages.size) {
      this.sendMessage(messages[0])
      return
    }
  }
}