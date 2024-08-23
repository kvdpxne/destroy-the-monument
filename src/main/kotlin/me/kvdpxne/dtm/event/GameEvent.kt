package me.kvdpxne.dtm.event

import me.kvdpxne.dtm.game.Game
import me.kvdpxne.dtm.game.Team
import me.kvdpxne.thrivi.Event

open class GameEvent(val game: Game<out Team>) : Event()