package me.kvdpxne.dtm.data.raw

import java.util.UUID

class RawMonumentPosition(
  // @formatter:off
  val identifier: UUID,
  val team      : RawTeam,
  val x         : Int,
  val y         : Int,
  val z         : Int
  // @formatter:on
)
