package me.kvdpxne.dtm.command

interface Performer {

  val name: String

  fun hasPermission(permission: String): Boolean

  fun sendMessage(message: String)
}