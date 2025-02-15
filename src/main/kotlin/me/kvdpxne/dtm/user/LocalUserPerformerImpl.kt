package me.kvdpxne.dtm.user

import java.lang.ref.Reference
import java.lang.ref.WeakReference
import java.util.Locale
import me.kvdpxne.dtm.shared.PlayerUuid
import me.kvdpxne.dtm.shared.player.sendPacket
import me.kvdpxne.dtm.shared.text.colorize
import org.bukkit.Bukkit
import org.bukkit.entity.Player

/**
 * Implementation of [LocalUserPerformer] that represents a player performer
 * associated with a specific [LocalUser]. It lazily resolves and stores the
 * player instance associated with the given identifier using a weak reference.
 *
 * @param identifier The unique identifier of the player.
 * @param user The associated [LocalUser] object.
 *
 * @since 0.1.0
 */
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
   * Lazily initializes and retrieves the associated [Player] object. If
   * the player is not already cached in the weak reference, it attempts to
   * resolve the player via Bukkit and caches the result.
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

  override fun sendPacket(packet: Any) {
    this.player?.sendPacket(packet)
  }

  /**
   * Executes the provided action on the associated player if they are online.
   * Does nothing if the player is offline.
   *
   * @param action The action to perform on the player.
   *
   * @since 0.1.0
   */
  private fun withPlayer(
    action: (Player) -> Unit
  ) {
    val player: Player? = this.player
    if (null != player && player.isOnline) {
      action(player)
    }
  }

  override fun sendMessage(
    message: String
  ) {
    this.withPlayer { player: Player ->
      player.sendMessage(message.colorize)
    }
  }

  override fun sendMessage(
    message: () -> String
  ) {
    this.withPlayer { player: Player ->
      player.sendMessage(message().colorize)
    }
  }

  override fun sendMessages(
    messages: Array<out String>
  ) {
    this.withPlayer { player: Player ->
      for (message: String in messages) {
        player.sendMessage(message.colorize)
      }
    }
  }

  override fun sendMessages(
    messages: () -> Array<out String>
  ) {
    this.withPlayer { player: Player ->
      for (message: String in messages()) {
        player.sendMessage(message.colorize)
      }
    }
  }

  override fun close() {
    this.player?.closeInventory()
  }
}