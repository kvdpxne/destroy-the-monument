package me.kvdpxne.dtm.game

import me.kvdpxne.dtm.shared.ancillary.Identifiable
import me.kvdpxne.dtm.shared.ancillary.Nameable
import org.bukkit.ChatColor
import org.bukkit.DyeColor

interface Team : Identifiable<String>, Nameable {

  /**
   * @since 0.1.0
   */
  val game: Game<out Team>

  /**
   * @since 0.1.0
   */
  val color: TeamColor

  /**
   * @since 0.1.0
   */
  val colorInChat: ChatColor
    get() = this.color.first

  /**
   * @since 0.1.0
   */
  val professionColor: ChatColor
    get() = this.color.second

  /**
   * @since 0.1.0
   */
  val dyeColor: DyeColor
    get() = this.color.third

  /**
   * @since 0.1.0
   */
  override val displayName: String
    get() = "${this.color.first}&l${this.name}"

  /**
   * @since 0.1.0
   */
  fun toLocalTeam(): LocalTeam
}