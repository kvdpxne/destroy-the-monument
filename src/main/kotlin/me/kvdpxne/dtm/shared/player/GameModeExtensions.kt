package me.kvdpxne.dtm.shared.player

import org.bukkit.GameMode

val GameMode.isSurvival: Boolean
  get() = GameMode.SURVIVAL == this

val GameMode.isSurvivalOrAdventure: Boolean
  get() = this.isSurvival || GameMode.ADVENTURE == this

val GameMode.isCreative: Boolean
  get() = this == GameMode.CREATIVE