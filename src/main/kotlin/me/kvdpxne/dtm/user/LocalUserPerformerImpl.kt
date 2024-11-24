package me.kvdpxne.dtm.user

import java.lang.ref.Reference
import java.lang.ref.WeakReference
import java.util.Locale
import me.kvdpxne.dtm.shared.PlayerUuid
import me.kvdpxne.dtm.shared.text.colorize
import org.bukkit.Bukkit
import org.bukkit.entity.Player

class LocalUserPerformerImpl(
  // @formatter:off
  override val identifier: PlayerUuid,
  override val user      : LocalUser
  // @formatter:on
) : LocalUserPerformer {

  /**
   * Reference to the associated [Player] object, using a weak reference
   * for memory efficiency.
   *
   * @since 0.1.0
   */
  private var _playerReference: Reference<Player>? = null

  /**
   * Lazily initializes and retrieves the associated player. If the player
   * is not already cached in the weak reference, it attempts to resolve the
   * player via Bukkit and caches the result.
   *
   * @since 0.1.0
   */
  private val _playerLazyDelegate: Lazy<Player?> = lazy {
    var player: Player? = this._playerReference?.get()
    if (null != player) {
      return@lazy player
    }
    player = Bukkit.getPlayer(this.identifier)
    this._playerReference = WeakReference(player)
    return@lazy player
  }

  override val name: String
    get() = this.user.name

  override val player: Player?
    get() = this._playerLazyDelegate.value

  override val locale: Locale
    get() = this.user.locale

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