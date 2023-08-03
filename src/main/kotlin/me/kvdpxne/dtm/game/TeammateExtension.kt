package me.kvdpxne.dtm.game

import me.kvdpxne.dtm.shared.Identity
import me.kvdpxne.dtm.user.User

fun User.toTeammate(identity: Identity, team: Team) = Teammate(this, identity, team)