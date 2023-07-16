package me.kvdpxne.dtm.game

import me.kvdpxne.dtm.shared.IdentifiableByName

class Team(val name: IdentifiableByName) {

  val teammates = mutableListOf<Teammate>()
}