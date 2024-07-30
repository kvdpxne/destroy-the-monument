package me.kvdpxne.dtm.uid

import com.github.f4b6a3.ulid.Ulid
import com.github.f4b6a3.ulid.UlidCreator
import java.util.UUID

/**
 * Provides a utility for generating universally unique identifiers (UUIDs).
 *
 * This class encapsulates the generation of UUIDs, ensuring consistency and
 * promoting code reusability. It offers methods for generating both
 * traditional Java UUIDs and ULIDs (Universally Unique Lexicographically
 * Sortable Identifiers).
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
  fun uuid(): String {
    return UUID.randomUUID().toString()
  }

  /**
   * Generates a new Universally Unique Lexicographically Sortable Identifier
   * (ULID).
   *
   * This method uses the `f4b6a3/ulid` library to generate a ULID. ULIDs are
   * time- and space-based identifiers that are sortable by their string
   * representation.
   *
   * @return A newly generated `Ulid` object.
   *
   * @since 0.1.0
   */
  fun standard(): Ulid {
    return UlidCreator.getUlid()
  }

  /**
   * Generates a new Universally Unique Lexicographically Sortable Identifier
   * (ULID) using the faster `fast()` method from the `f4b6a3/ulid` library.
   *
   * This method may be slightly less secure than `standard()` but offers
   * improved performance.
   *
   * @return A newly generated `Ulid` object.
   *
   * @since 0.1.0
   */
  fun fast(): Ulid {
    return Ulid.fast()
  }

  /**
   * Generates a new Universally Unique Lexicographically Sortable Identifier
   * (ULID) as a lowercase string.
   *
   * This method calls `standard()` to generate a ULID and then converts it to
   * a lowercase string representation.
   *
   * @return A newly generated `UUID` in lowercase string format.
   *
   * @since 0.1.0
   */
  fun next(): String {
    return this.standard().toLowerCase()
  }
}

/**
 * @since 0.1.0
 */
fun String.toUuid(): UUID {
  return UUID.fromString(this)
}

/**
 * @since 0.1.0
 */
fun String.toUlid(): Ulid {
  return Ulid.from(this)
}