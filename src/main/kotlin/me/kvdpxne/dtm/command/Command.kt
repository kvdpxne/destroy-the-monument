package me.kvdpxne.dtm.command

open class Command(
  val name       : String,
  val description: String = "",
  val usage      : String = "",
  val aliases    : Array<out String> = emptyArray(),
  val permission : String = "",
  val executionType: ExecutionPlaceType = ExecutionPlaceType.EVERYWHERE
)