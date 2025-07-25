package me.kvdpxne.dtm.raw.factories

import java.util.UUID
import me.kvdpxne.dtm.data.raw.RawArena
import me.kvdpxne.dtm.data.raw.RawArenaMap
import me.kvdpxne.dtm.data.raw.RawMonumentPosition
import me.kvdpxne.dtm.data.raw.RawRevivalPosition
import me.kvdpxne.dtm.data.raw.RawTeam
import me.kvdpxne.dtm.raw.NAMES_OF_ARENAS
import me.kvdpxne.dtm.shared.randomInt
import me.kvdpxne.dtm.shared.uniqueString
import me.kvdpxne.dtm.shared.uniqueUuid

/**
 * Generates monuments for all teams in an arena (2-5 monuments per team).
 *
 * @param teams Teams requiring monument positions.
 * @return List of [RawMonumentPosition] linked to input teams.
 *
 * @since 0.1.0
 * @see makeRawMonumentPosition
 */
private fun fillMonumentPositions(
  teams: List<RawTeam>
): List<RawMonumentPosition> {
  val size = randomInt(2, 5)
  val capacity = size * teams.size
  return buildList(capacity) {
    repeat(size) { _: Int ->
      teams.forEach { team: RawTeam ->
        this.add(makeRawMonumentPosition(team = team))
      }
    }
  }
}

/**
 * Generates one revival position per team in an arena.
 *
 * @param teams Teams requiring spawn points.
 * @return List of [RawRevivalPosition] (one per team).
 *
 * @since 0.1.0
 * @see makeRawRevivalPosition
 */
private fun fillRevivalPositions(
  teams: List<RawTeam>
): List<RawRevivalPosition> {
  return buildList(teams.size) {
    teams.forEach { team: RawTeam ->
      this.add(makeRawRevivalPosition(team = team))
    }
  }
}

/**
 * Factory function that generates a fully configured [RawArena] with randomized properties.
 * Creates a complete battle arena with teams, map, monuments, and spawn points.
 *
 * Default behaviors:
 * - Teams: 2-6 randomized teams
 * - Map: Random arena map name
 * - Monuments: 2-5 per team at random locations
 * - Revival: One spawn per team with random position/orientation
 * - Name: Random arena name
 *
 * @param previousIdentifier Optional UUID to avoid when generating a new identifier.
 * @param filledTeams List of participating teams (default: 2-6 random teams).
 * @param identifier Unique arena ID (default: conflict-free UUID).
 * @param map Associated arena map (default: randomized).
 * @param monumentPositions Monument locations (default: 2-5 per team).
 * @param revivalPositions Team spawn points (default: one per team).
 * @param name Display name (default: random from `NAMES_OF_ARENAS`).
 * @return Fully configured [RawArena] instance.
 *
 * @since 0.1.0
 * @see filledTeams
 * @see fillMonumentPositions
 * @see fillRevivalPositions
 * @see uniqueUuid
 * @see makeRawArenaMap
 * @see NAMES_OF_ARENAS
 */
fun makeRawArena(
  // @formatter:off
  previousIdentifier: UUID?                     = null,
  previousName      : String?                   = null,
  filledTeams       : List<RawTeam>             = fillTeams(),
  identifier        : UUID                      = uniqueUuid(previousIdentifier),
  name              : String                    = uniqueString(previousName, NAMES_OF_ARENAS),
  map               : RawArenaMap               = makeRawArenaMap(),
  monumentPositions : List<RawMonumentPosition> = fillMonumentPositions(filledTeams),
  revivalPositions  : List<RawRevivalPosition>  = fillRevivalPositions(filledTeams),
  // @formatter:on
) = RawArena(
  identifier,
  map,
  monumentPositions,
  revivalPositions,
  name
)