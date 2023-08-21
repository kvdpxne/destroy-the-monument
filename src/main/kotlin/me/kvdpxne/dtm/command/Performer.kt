package me.kvdpxne.dtm.command

interface Performer : Communicative {

  val name: String

  fun hasPermission(permission: String): Boolean
}