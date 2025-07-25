package me.kvdpxne.dtm.data.raw

import java.util.UUID
import me.kvdpxne.dtm.data.shared.Raw
import me.kvdpxne.dtm.shared.StylishToStringBuilder
import me.kvdpxne.dtm.util.StylishToString
import me.kvdpxne.dtm.util.StylishToStringProvider

/**
 * @property identifier
 * @property map
 * @property monumentPositions
 * @property revivalPositions
 * @property name
 *
 * @author Łukasz Pietrzak (kvdpxne)
 * @since 0.1.0
 */
data class RawArena(
  val identifier: UUID,
  val map: RawArenaMap?,
  val monumentPositions: Collection<RawMonumentPosition>,
  val revivalPositions: Collection<RawRevivalPosition>,
  val name: String,
//  val displayName: String?,
//  val description: String?,
) : Raw, StylishToStringProvider {

  /**
   * @since 0.1.0
   */
  override fun toStylishString(): StylishToString {
    return StylishToStringBuilder
      .begin("RawArena")
      .add("identifier", this.identifier)
      .addNested("map", this.map)
      .addNested("monumentPositions", this.monumentPositions)
      .addNested("revivalPositions", this.revivalPositions)
      .add("name", this.name)
  }

  /**
   * @since 0.1.0
   */
  override fun toString(): String {
    return this.toStylishString().packed()
  }
}