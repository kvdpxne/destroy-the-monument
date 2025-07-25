package me.kvdpxne.dtm.data.mapping

import me.kvdpxne.dtm.data.raw.RawTeam
import me.kvdpxne.dtm.data.shared.mapNotNullTo
import me.kvdpxne.dtm.team.Team

/**
 * @since 0.1.0
 */
fun Team.toRawTeam(): RawTeam {
  return RawTeam(
    this.identifier,
    this.name,
    this.colorOfArmor.asFullFormat,
    this.colorOfProfession.asFullFormat,
    this.colorOnChat.asFullFormat,
    this.colorOnPlayerList?.asFullFormat,
  )
}

/**
 * @param initialCapacity
 * @param distinct
 *
 * @since 0.1.0
 */
fun Iterable<Team?>.toRawTeams(
  initialCapacity: Int = 12,
  distinct: Boolean = true
): List<RawTeam> {
  return mapNotNullTo(
    this,
    initialCapacity,
    distinct,
    Team::toRawTeam
  )
}