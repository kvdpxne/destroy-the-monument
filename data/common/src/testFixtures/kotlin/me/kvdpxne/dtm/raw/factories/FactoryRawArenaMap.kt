package me.kvdpxne.dtm.raw.factories

import java.util.UUID
import me.kvdpxne.dtm.data.raw.RawArenaMap
import me.kvdpxne.dtm.data.raw.RawArenaMapRules
import me.kvdpxne.dtm.raw.NAMES_OF_ARENA_MAPS
import me.kvdpxne.dtm.shared.uniqueString
import me.kvdpxne.dtm.shared.uniqueUuid

/**
 * Factory function that generates a [RawArenaMap] (battle arena configuration) with
 * randomized or configurable properties. Map names are randomly selected from predefined options.
 *
 * @param previousIdentifier Optional UUID to avoid when generating a new identifier.
 * @param identifier Unique arena map ID (default: new UUID unique relative to `previousIdentifier`).
 * @param name Display name for the arena map (default: random value from `NAMES_OF_ARENA_MAPS`).
 * @return Configured [RawArenaMap] instance.
 *
 * @since 0.1.0
 * @see uniqueUuid
 * @see NAMES_OF_ARENA_MAPS
 */
fun makeRawArenaMap(
  // @formatter:off
  previousIdentifier: UUID?            = null,
  previousName      : String?          = null,
  identifier        : UUID             = uniqueUuid(previousIdentifier),
  name              : String           = uniqueString(previousName, NAMES_OF_ARENA_MAPS)
  // @formatter:on
) = RawArenaMap(
  identifier,
  name
)