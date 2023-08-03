package me.kvdpxne.dtm.game

import me.kvdpxne.dtm.shared.Identity
import me.kvdpxne.dtm.user.User

class Teammate(
  val user: User,
  var teamColor: Identity,
  val team: Team
)