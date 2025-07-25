package me.kvdpxne.dtm.raw.factories

import java.util.UUID
import me.kvdpxne.dtm.data.raw.RawArena
import me.kvdpxne.dtm.data.raw.RawGame
import me.kvdpxne.dtm.data.raw.RawTeam
import me.kvdpxne.dtm.raw.NAMES_OF_ARENAS
import me.kvdpxne.dtm.shared.randomInt
import me.kvdpxne.dtm.shared.uniqueString
import me.kvdpxne.dtm.shared.uniqueUuid

private fun fillArenas(
  teams: List<RawTeam>
): List<RawArena> {
  val capacity = randomInt(10, 20)
  return buildList(capacity) {
    repeat(capacity) {
      this.add(makeRawArena(filledTeams = teams))
    }
  }
}

fun makeRawGame(
  // @formatter:off
  previousIdentifier: UUID?          = null,
  previousName      : String?        = null,
  identifier        : UUID           = uniqueUuid(previousIdentifier),
  name              : String         = uniqueString(previousName, NAMES_OF_ARENAS),
  teams             : List<RawTeam>  = fillTeams(),
  arenas            : List<RawArena> = fillArenas(teams)
  // @formatter:on
) = RawGame(
  identifier,
  teams,
  arenas,
  name
)