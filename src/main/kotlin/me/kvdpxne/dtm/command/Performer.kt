package me.kvdpxne.dtm.command

import me.kvdpxne.dtm.shared.ancillary.Communicative

interface Performer : Communicative {

  val name: String

  fun hasPermission(permission: String): Boolean
}