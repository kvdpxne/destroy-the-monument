package me.kvdpxne.dtm.data.raw

import java.util.UUID

/**
 * @since 0.1.0
 */
data class RawUser(
  // @formatter:off
  val identifier : UUID,
  val statistics : RawUserStatistics,
  val wallet     : RawUserWallet,
  val name       : String,
  val displayName: String?,
  val profession : String,
  val locale     : String
  // @formatter:on
) {

  /**
   * @since 0.1.0
   */
  companion object
}
