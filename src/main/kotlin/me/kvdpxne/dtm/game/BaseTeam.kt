package me.kvdpxne.dtm.game

import me.kvdpxne.dtm.shared.ancillary.AbstractIdentifiable
import me.kvdpxne.dtm.uid.Uid

open class BaseTeam(
  // @formatter:off
  override val name      : String,
  override val color     : TeamColor,
               identifier: String = Uid.next()
  // @formatter:on
) : AbstractIdentifiable<String>(identifier), Team {

  override val game: Game<out Team>
    get() = TODO("Not yet implemented")

  /**
   * @since 0.1.0
   */
  override fun toLocalTeam(): LocalTeam {
    return BaseLocalTeam(
      this.name,
      this.color,
      this.identifier,
      mutableSetOf()
    )
  }
}