package me.kvdpxne.dtm.user

import me.kvdpxne.dtm.command.Performer
import me.kvdpxne.dtm.shared.isTrue
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import java.lang.ref.Reference
import java.lang.ref.WeakReference
import java.util.*

class UserPerformer(
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
    isTrue(permission.isBlank(), "?")
    return getPlayer()?.hasPermission(permission) ?: false
  }


  override fun sendMessage(message: String) {
    getPlayer()?.sendMessage(message)
  }
}