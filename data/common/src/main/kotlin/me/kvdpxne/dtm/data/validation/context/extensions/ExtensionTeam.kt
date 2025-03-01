package me.kvdpxne.dtm.data.validation.context.extensions

import me.kvdpxne.dtm.data.raw.RawTeam
import me.kvdpxne.dtm.data.validation.context.isTeamValid

/**
 * @since 0.1.0
 */
fun RawTeam?.validate(): Int {
  return isTeamValid(this)
}