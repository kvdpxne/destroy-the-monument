package me.kvdpxne.dtm.shared

import java.util.UUID

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
fun uniqueUuid(
  previous: UUID? = null
): UUID {
  return of(previous, { UUID.randomUUID() })
}