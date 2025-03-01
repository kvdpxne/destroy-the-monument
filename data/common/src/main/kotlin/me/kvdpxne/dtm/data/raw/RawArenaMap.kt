package me.kvdpxne.dtm.data.raw

import java.util.UUID

/**
 * @since 0.1.0
 */
data class RawArenaMap(
  // @formatter:off
  val identifier: UUID,
  val name      : String
  // @formatter:on
) {

  /**
   * @since 0.1.0
   */
  companion object
}
