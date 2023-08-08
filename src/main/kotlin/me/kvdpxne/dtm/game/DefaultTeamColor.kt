package me.kvdpxne.dtm.game

import me.kvdpxne.dtm.shared.Identity
import org.bukkit.ChatColor
import org.bukkit.DyeColor
import java.util.UUID

enum class DefaultTeamColor(
  // TODO DXX
  override val identifier: UUID,
  override val key: String,
  val chatColor: ChatColor,
  val dyeColor: DyeColor
) : Identity {

  BLUE(
    UUID.fromString("0e97e38e-2123-4e17-bfe1-33031b35e08c"),
    "blue",
    ChatColor.BLUE,
    DyeColor.BLUE
  ),

  RED(
    UUID.fromString("7a34aa4a-4979-4a46-9b7c-1ec2530e239d"),
    "red",
    ChatColor.DARK_RED,
    DyeColor.RED
  );

  companion object {

    fun findByIdentity(identity: Identity) = entries.find {
      it.key == identity.key
    }

    fun findByIdentityKey(key: String) = entries.find {
      it.key.equals(key, true)
    }
  }
}