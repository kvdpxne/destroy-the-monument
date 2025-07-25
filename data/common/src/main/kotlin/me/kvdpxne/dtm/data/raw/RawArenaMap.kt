package me.kvdpxne.dtm.data.raw

import java.util.UUID
import me.kvdpxne.dtm.data.shared.Raw
import me.kvdpxne.dtm.shared.StylishToStringBuilder
import me.kvdpxne.dtm.util.StylishToString
import me.kvdpxne.dtm.util.StylishToStringProvider

/**
 * @property identifier
 * @property name
 *
 * @author Łukasz Pietrzak (kvdpxne)
 * @since 0.1.0
 */
data class RawArenaMap(
  val identifier: UUID,
  val name: String
) : Raw, StylishToStringProvider {

  override fun toStylishString(): StylishToString {
    return StylishToStringBuilder
      .begin("RawArenaMap")
      .add("identifier", this.identifier)
      .add("name", this.name)
  }

  override fun toString(): String {
    return this.toStylishString().packed()
  }
}
