package me.kvdpxne.dtm.data.raw

import java.util.UUID
import me.kvdpxne.dtm.data.shared.Raw
import me.kvdpxne.dtm.shared.StylishToStringBuilder
import me.kvdpxne.dtm.util.StylishToString
import me.kvdpxne.dtm.util.StylishToStringProvider

/**
 * @property identifier
 * @property name
 * @property colorOfArmor
 * @property colorOfProfession
 * @property colorOnChat
 * @property colorOnPlayerList
 *
 * @author Łukasz Pietrzak (kvdpxne)
 * @since 0.1.0
 */
data class RawTeam(
  val identifier: UUID,
  val name: String,
  val colorOfArmor: String,
  val colorOfProfession: String,
  val colorOnChat: String,
  val colorOnPlayerList: String?
) : Raw, StylishToStringProvider {

  /**
   * @since 0.1.0
   */
  override fun toStylishString(): StylishToString {
    return StylishToStringBuilder
      .begin("RawTeam")
      .add("identifier", this.identifier)
      .add("name", this.name)
      .add("colorOfArmor", this.colorOfArmor)
      .add("colorOfProfession", this.colorOfProfession)
      .add("colorOnChat", this.colorOnChat)
      .add("colorOnPlayerList", this.colorOnPlayerList)
  }

  /**
   * @since 0.1.0
   */
  override fun toString(): String {
    return this.toStylishString().packed()
  }
}