package me.kvdpxne.dtm.data.raw

import java.util.UUID
import org.jetbrains.annotations.VisibleForTesting

/**
 * @since 0.1.0
 */
data class RawUser @VisibleForTesting constructor(
  // @formatter:off
  val identifier : UUID,
  val statistics : RawUserStatistics,
  val wallet     : RawUserWallet,
  val name       : String,
  val displayName: String?,
  val profession : String,
  val locale     : String
  // @formatter:on
)
