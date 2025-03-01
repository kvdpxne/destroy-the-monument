package me.kvdpxne.dtm.data.raw

import java.util.UUID

/**
 * @since 0.1.0
 */
data class RawTeam(
  // @formatter:off
  val identifier       : UUID,
  val name             : String,
  val colorOfArmor     : String,
  val colorOfProfession: String,
  val colorOnChat      : String,
  val colorOnPlayerList: String?
  // @formatter:on
) {

  /**
   * @since 0.1.0
   */
  companion object
}