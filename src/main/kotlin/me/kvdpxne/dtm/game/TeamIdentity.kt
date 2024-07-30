package me.kvdpxne.dtm.game

import me.kvdpxne.dtm.shared.ancillary.AbstractIdentifiable
import me.kvdpxne.dtm.uid.Uid
import org.bukkit.ChatColor
import org.bukkit.DyeColor

class TeamIdentity(
  // @formatter:off
  val name           : String,
  val colorInChat    : ChatColor,
  val professionColor: ChatColor,
  val dyeColor       : DyeColor,
      identifier     : String = Uid.next()
  // @formatter:on
) : AbstractIdentifiable<String>(identifier) {


  fun displayName(): String {
    return "${this.colorInChat}&l${this.name}"
  }
}