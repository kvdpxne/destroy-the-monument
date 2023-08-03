package me.kvdpxne.dtm.game

import me.kvdpxne.dtm.shared.Identity
import org.bukkit.ChatColor
import org.bukkit.DyeColor

enum class DefaultTeamColor(
  override val identifiableName: String,
  val chatColor: ChatColor,
  val dyeColor: DyeColor
) : Identity {

  BLUE("blue", ChatColor.BLUE, DyeColor.BLUE),
  RED("red", ChatColor.DARK_RED, DyeColor.RED);

  companion object {

    fun findByIdentity(identity: Identity) = entries.find {
      it.identifiableName == identity.identifiableName
    }

    fun findByIdentityKey(key: String) = entries.find {
      it.identifiableName.equals(key, true)
    }
  }
}