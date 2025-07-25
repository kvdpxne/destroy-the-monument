package me.kvdpxne.dtm.raw.factories

import java.util.UUID
import me.kvdpxne.dtm.data.raw.RawTeam
import me.kvdpxne.dtm.raw.NAMES_OF_TEAMS
import me.kvdpxne.dtm.shared.randomHexColor
import me.kvdpxne.dtm.shared.randomInt
import me.kvdpxne.dtm.shared.randomMinecraftColor
import me.kvdpxne.dtm.shared.uniqueString
import me.kvdpxne.dtm.shared.uniqueUuid

/**
 * Generates a randomized list of teams (2-6 teams) for arena configuration.
 *
 * @return List of [RawTeam] instances with randomized properties.
 *
 * @since 0.1.0
 * @see makeRawTeam
 */
internal fun fillTeams(): List<RawTeam> {
  val capacity = randomInt(2, 6)
  return buildList(capacity) {
    repeat(capacity) { _: Int ->
      this.add(makeRawTeam())
    }
  }
}

/**
 * Factory function that generates a [RawTeam] instance with randomized or configurable properties.
 *
 * Default behaviors:
 * - Team name randomly selected from predefined `NAMES_OF_TEAMS`
 * - Colors randomized using Minecraft and hexadecimal generators
 * - UUID conflict prevention via `uniqueUuid()`
 *
 * @param previousIdentifier Optional UUID to avoid when generating a new unique identifier.
 * @param identifier Unique team ID (default: new UUID unique relative to `previousIdentifier`).
 * @param name Team display name (default: random value from `NAMES_OF_TEAMS`).
 * @param colorOfArmor Armor color in hexadecimal format (default: random RGB hex).
 * @param colorOfProfession Profession UI color in hexadecimal format (default: random RGB hex).
 * @param colorOnChat Chat prefix color code (default: random Minecraft color code like `&a`).
 * @param colorOnPlayerList Tablist color code (default: random Minecraft color code; nullable).
 * @return Configured [RawTeam] instance.
 *
 * @since 0.1.0
 * @see uniqueUuid
 * @see randomHexColor
 * @see randomMinecraftColor
 * @see NAMES_OF_TEAMS
 */
fun makeRawTeam(
  // @formatter:off
  previousIdentifier: UUID?   = null,
  previousName      : String? = null,
  identifier        : UUID    = uniqueUuid(previousIdentifier),
  name              : String  = uniqueString(previousName, NAMES_OF_TEAMS),
  colorOfArmor      : String  = randomHexColor(),
  colorOfProfession : String  = randomHexColor(),
  colorOnChat       : String  = randomMinecraftColor(),
  colorOnPlayerList : String? = randomMinecraftColor()
  // @formatter:on
) = RawTeam(
  identifier,
  name,
  colorOfArmor,
  colorOfProfession,
  colorOnChat,
  colorOnPlayerList
)