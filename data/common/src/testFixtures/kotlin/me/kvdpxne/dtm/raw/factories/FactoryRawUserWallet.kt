package me.kvdpxne.dtm.raw.factories

import java.util.UUID
import me.kvdpxne.dtm.data.raw.RawUserWallet
import me.kvdpxne.dtm.shared.randomBoolean
import me.kvdpxne.dtm.shared.randomFloat
import me.kvdpxne.dtm.shared.randomPositiveLong
import me.kvdpxne.dtm.shared.specified.randomCoins
import me.kvdpxne.dtm.shared.specified.randomMultiplier
import me.kvdpxne.dtm.shared.uniqueUuid

/**
 * Generates a [RawUserWallet] with randomized valid or custom parameters for testing.
 *
 * @param previousIdentifier If provided, ensures new `identifier` is unique relative to this [UUID].
 * @param identifier Wallet [UUID] (default: random unique [UUID]).
 * @param coins Coin balance (default: random positive Long; must be ≥ 0).
 * @param multiplier Earning multiplier (default: random float in [[MIN_MULTIPLIER], [MAX_MULTIPLIER]]).
 * @param infinite Infinite coins flag (default: random boolean).
 * @param locked Wallet lock status (default: random boolean).
 * @return Configured [RawUserWallet] instance.
 *
 * @since 0.1.0
 * @see uniqueUuid
 * @see randomPositiveLong
 * @see randomFloat
 * @see randomBoolean
 */
fun makeRawUserWallet(
  // @formatter:off
  previousIdentifier: UUID?   = null,
  identifier        : UUID    = uniqueUuid(previousIdentifier),
  coins             : Long    = randomCoins(),
  multiplier        : Float   = randomMultiplier(),
  infinite          : Boolean = randomBoolean(),
  locked            : Boolean = randomBoolean()
  // @formatter:on
) = RawUserWallet(
  identifier,
  coins,
  multiplier,
  infinite,
  locked
)