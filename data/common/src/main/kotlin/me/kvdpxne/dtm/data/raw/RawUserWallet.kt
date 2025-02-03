package me.kvdpxne.dtm.data.raw

import java.util.UUID
import org.jetbrains.annotations.VisibleForTesting

/**
 * Represents a raw user wallet, serving as an intermediate representation
 * between core logic and the database.
 *
 * **Important:** The `identifier` field is expected to be a UUID without
 * hyphens. While UUIDs are typically represented with hyphens, using a plain
 * string format allows for greater flexibility when working with various
 * databases that may not have native UUID support (e.g., SQLite).
 *
 * **Note:** Due to the nature of this class as a raw data representation, it is not recommended to rely on
 * `hashCode()` or `equals()` for comparison purposes. These methods are not overridden and may produce
 * unexpected results.
 *
 * **Note:** This class is intended for internal use and should not be directly exposed to external components.
 * It is not recommended to use `hashCode()` or `equals()` on instances of this class.
 *
 * @param identifier A unique identifier for the user wallet, represented as a string without hyphens.
 * @param coins The current number of coins in the user's wallet.
 * @param multiplier A multiplier applied to various calculations involving the wallet.
 * @since 0.1.0
 */
data class RawUserWallet @VisibleForTesting constructor(
  // @formatter:off
  val identifier: UUID,
  val coins     : Long,
  val multiplier: Float,
  val infinite  : Boolean,
  val locked    : Boolean
  // @formatter:on
) {

  /**
   * @since 0.1.0
   */
  companion object
}
