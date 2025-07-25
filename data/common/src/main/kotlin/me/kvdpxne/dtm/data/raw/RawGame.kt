package me.kvdpxne.dtm.data.raw

import java.util.UUID
import me.kvdpxne.dtm.data.shared.Raw
import me.kvdpxne.dtm.shared.StylishToStringBuilder
import me.kvdpxne.dtm.util.StylishToString
import me.kvdpxne.dtm.util.StylishToStringProvider

/**
 * @property identifier
 * @property teams
 * @property arenas
 * @property name
 *
 * @author Łukasz Pietrzak (kvdpxne)
 * @since 0.1.0
 */
data class RawGame(
  val identifier: UUID,
  val teams: Collection<RawTeam>,
  val arenas: Collection<RawArena>,
  val name: String
) : Raw, StylishToStringProvider {

  /**
   * @since 0.1.0
   */
  override fun toStylishString(): StylishToString {
    return StylishToStringBuilder
      .begin("RawGame")
      .add("identifier", this.identifier)
      .addNested("teams", this.teams)
      .addNested("arenas", this.arenas)
      .add("name", this.name)
  }

  /**
   * @since 0.1.0
   */
  override fun toString(): String {
    return this.toStylishString().packed()
  }
}