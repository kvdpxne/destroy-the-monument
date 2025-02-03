package me.kvdpxne.dtm.data.raw

class RawGame(
  val identifier: String,
  val arenas: Collection<RawArena>,
  val teams: Collection<RawTeam>,
  val name: String
)