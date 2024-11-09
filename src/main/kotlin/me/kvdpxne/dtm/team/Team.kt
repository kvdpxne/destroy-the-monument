package me.kvdpxne.dtm.team

import me.kvdpxne.dtm.game.Game
import me.kvdpxne.dtm.shared.Identifiable
import me.kvdpxne.dtm.shared.Nameable
import me.kvdpxne.dtm.shared.TeamUuid
import org.bukkit.ChatColor
import org.bukkit.DyeColor

/**
 * @since 0.1.0
 */
interface Team : Identifiable<TeamUuid>, Nameable {

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