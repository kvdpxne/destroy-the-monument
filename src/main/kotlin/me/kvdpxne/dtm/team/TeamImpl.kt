package me.kvdpxne.dtm.team

import java.util.UUID
import me.kvdpxne.dtm.game.Game
import me.kvdpxne.dtm.shared.AbstractIdentifiable
import me.kvdpxne.dtm.shared.StylishToStringBuilder

open class TeamImpl(
  // @formatter:off
  override val name      : String,
  override val color     : TeamColor,
               identifier: UUID = UUID.randomUUID()
  // @formatter:on
) : AbstractIdentifiable<UUID>(identifier), Team {

  override val game: Game<out Team>
    get() = TODO("Not yet implemented")

  /**
   * @since 0.1.0
   */
  override fun toLocalTeam(): LocalTeam {
    return LocalTeamImpl(
      this.name,
      this.color,
      this.identifier,
      mutableSetOf()
    )
  }

  override fun equals(
    other: Any?
  ): Boolean {
    if (this === other) {
      return true
    }

    if (other !is Team) {
      return false
    }

    return super.equals(other)
  }

  override fun hashCode(): Int {
    return super.hashCode()
  }

  override fun toString(): String {
    return StylishToStringBuilder()
      .begin("Team")
      .add("identifier", this.identifier)
      .add("name", this.name)
      .build()
  }
}