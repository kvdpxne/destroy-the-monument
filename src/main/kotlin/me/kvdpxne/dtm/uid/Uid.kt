package me.kvdpxne.dtm.uid

import java.util.UUID

/**
 * Provides a utility for generating universally unique identifiers (UUIDs)
 * in string format.
 *
 * This class encapsulates the generation of UUIDs, ensuring consistency and
 * promoting code reusability.
 *
 * @since 0.1.0
 */
object Uid {

  /**
   * Generates a new universally unique identifier (UUID) as a string.
   *
   * This method leverages the `java.util.UUID` class to create a new `UUID`
   * and returns its string representation.
   *
   * @return A newly generated `UUID` in string format.
   *
   * @since 0.1.0
   */
  fun next(): String {
    return UUID.randomUUID().toString()
  }
}