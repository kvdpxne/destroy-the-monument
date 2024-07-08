package me.kvdpxne.dtm.game

import me.kvdpxne.dtm.user.User

/**
 *
 */
fun User.toTeammate(team: Team): Teammate {
  return Teammate(this, team)
}