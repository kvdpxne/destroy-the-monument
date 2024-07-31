package me.kvdpxne.dtm.game

import me.kvdpxne.dtm.shared.ancillary.AbstractIdentifiable
import me.kvdpxne.dtm.uid.Uid
import org.bukkit.ChatColor
import org.bukkit.DyeColor

class TeamIdentity(
  // @formatter:off
  val name      : String,
  val color     : TeamColor,
      identifier: String = Uid.next()
  // @formatter:on
) : AbstractIdentifiable<String>(identifier) {

  val colorInChat: ChatColor
    get() = this.color.first

  val professionColor: ChatColor
    get() = this.color.second

  val dyeColor: DyeColor
    get() = this.color.third

  fun displayName(): String {
    return "${this.color.first}&l${this.name}"
  }
}