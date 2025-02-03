package me.kvdpxne.dtm.data.util

import java.util.UUID

/**
 * Utility object for generating unique UUIDs.
 *
 * This object provides a method for generating version 4 UUIDs that ensures the
 * generated UUID is unique compared to a previously specified UUID.
 *
 * @since 0.1.0
 */
object UniqueUuid {

  /**
   * Generates a version 4 [UUID] that is guaranteed to be different from the
   * specified previous [UUID].
   *
   * This method uses a loop to repeatedly generate random [UUID]s until one is
   * found that does not match the provided previous [UUID]. If no previous
   * [UUID] is specified, it generates a random [UUID] directly.
   *
   * @param previous an optional [UUID] to compare against. If specified, the
   * generated UUID will not be the same as this value.
   * @return a unique version 4 [UUID].
   * @since 0.1.0
   */
  fun v4(
    previous: UUID? = null
  ): UUID {
    var next: UUID
    do {
      next = UUID.randomUUID()
    } while (next == previous)
    return next
  }
}