package me.kvdpxne.dtm.user.contractor

import java.lang.ref.Reference
import me.kvdpxne.dtm.shared.toSingleLines
import me.kvdpxne.dtm.user.LocalUser
import me.kvdpxne.dtm.user.UserInvalidNameException
import org.bukkit.Bukkit
import org.bukkit.OfflinePlayer
import org.bukkit.entity.Player
import org.bukkit.permissions.Permission
import org.bukkit.permissions.PermissionAttachmentInfo

class BukkitUserContractor(
  localUser: LocalUser
) : BasicUserContractor(localUser) {

  override fun getPlayerOrNull(): Any? {
    val playerReference: Reference<Any?>? = this.playerReference
    var player: Player?

    if (null == playerReference) {
      player = Bukkit.getPlayer(this.getIdentifier())
      if (null != player) {
        /* Przechowywana w pamięci tymczasowej nazwa lokalnego użytkownika
         * powinna być taka sama jak nazwa gracza przechowywanego w lokalnej
         * pamięci, lecz w zależności od implementacji, używania starszych
         * wersji platformy Bukkit lub po poprzez ludzki błąd to założenie może
         * nie być do końca spełnione, dlatego poniższy warunek ma na celu
         * wyłapanie takiego scenariusza.
         */
        if (this.name.equals(player.name, true)) {
          throw UserInvalidNameException(
            """

            """.toSingleLines()
          )
        }

        return this.updateReference(player)
      }

      player = Bukkit.getPlayer(this.name)
      if (null != player) {
        return this.updateReference(player)
      }

      return null
    }

    player = playerReference.get() as Player?
    if (null != player) {
      return player
    }

    return null
  }

  override fun isOnline(): Boolean {
    val player: Player = this.playerOrNull as Player?
      ?: return false

    return player.isOnline
  }

  override fun isOperator(): Boolean {
    val player: Player = this.playerOrNull as Player?
      ?: return false

    return player.isOp
  }

  /**
   * @since 0.1.0
   */
  private fun hasPrivilegeAsOfflineOperator(): Byte {
    val offlinePlayer: OfflinePlayer = Bukkit.getOfflinePlayer(this.getIdentifier())
      ?: return PrivilegeAccess.CANNOT_BE_DETERMINED

    if (Permission.DEFAULT_PERMISSION.getValue(offlinePlayer.isOp)) {
      return PrivilegeAccess.PRIVILEGES_HELD
    }

    return PrivilegeAccess.LACK_OF_PRIVILEGES
  }

  /**
   * @since 0.1.0
   */
  private fun hasPrivilegeAsDefault(
    privilege: Permission?
  ): Byte {
    if (null == privilege) {
      if (Permission.DEFAULT_PERMISSION.getValue(true)) {
        return PrivilegeAccess.PRIVILEGES_HELD
      }
      return PrivilegeAccess.LACK_OF_PRIVILEGES
    }

    if (privilege.default.getValue(true)) {
      return PrivilegeAccess.PRIVILEGES_HELD
    }

    return PrivilegeAccess.LACK_OF_PRIVILEGES
  }

  override fun hasPrivilegeAsOperator(
    privilege: String?,
    offline: Boolean
  ): Byte {
    require(!privilege.isNullOrBlank()) {
      """

      """.toSingleLines()
    }

    val player: Player? = this.playerOrNull as Player?
    if (null == player) {
      if (!offline) {
        return PrivilegeAccess.CANNOT_BE_DETERMINED
      }
      return this.hasPrivilegeAsOfflineOperator()
    }

    if (!player.isOp) {
      return PrivilegeAccess.LACK_OF_PRIVILEGES
    }

    return this.hasPrivilegeAsDefault(
      Bukkit.getPluginManager().getPermission(
        privilege.lowercase()
      )
    )
  }

  override fun hasPrivilege(
    privilege: String?,
    operator: Boolean,
    offline: Boolean
  ): Byte {
    require(!privilege.isNullOrBlank()) {
      """

      """.toSingleLines()
    }

    val player: Player? = this.playerOrNull as Player?
    if (null == player) {
      if (!operator || !offline) {
        return PrivilegeAccess.CANNOT_BE_DETERMINED
      }
      return this.hasPrivilegeAsOfflineOperator()
    }

    val lowerPrivilege: String = privilege.lowercase()
    if (operator) {
      if (!player.isOp) {
        return PrivilegeAccess.LACK_OF_PRIVILEGES
      }

      return this.hasPrivilegeAsDefault(
        Bukkit.getPluginManager().getPermission(lowerPrivilege)
      )
    }

    for (attachmentInfo: PermissionAttachmentInfo in player.effectivePermissions) {
      if (lowerPrivilege == attachmentInfo.permission) {
        return PrivilegeAccess.PRIVILEGES_HELD
      }
    }

    return PrivilegeAccess.LACK_OF_PRIVILEGES
  }
}