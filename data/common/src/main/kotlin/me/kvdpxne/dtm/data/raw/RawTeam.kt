package me.kvdpxne.dtm.data.raw

import java.util.UUID
import org.jetbrains.annotations.VisibleForTesting

data class RawTeam @VisibleForTesting constructor(
  // @formatter:off
  val identifier : UUID,
  val name       : String,
  val displayName: String?,
  val color      : String
  // @formatter:on
)