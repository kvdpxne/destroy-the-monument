package me.kvdpxne.dtm.listener.internal

import me.kvdpxne.dtm.event.GameStopEvent
import me.kvdpxne.dtm.shared.hardClean
import me.kvdpxne.dtm.user.UserPerformer
import me.kvdpxne.thrivi.EventHandler
import me.kvdpxne.thrivi.Listenable
import me.kvdpxne.thrivi.StandardEventPriorities
import org.bukkit.Bukkit

object GameStopListener : Listenable {

  @EventHandler(priority = StandardEventPriorities.HIGH)
  fun handleGameStop(event: GameStopEvent) {
    //
    val game = event.game

    game.teams.forEach { team ->
      team.teammateMutableSet.forEach { teammate ->

        val performer = teammate.user.performer as UserPerformer

        teammate.fastBoard!!.delete()
        teammate.fastBoard = null

        performer.getPlayer()!!.run {
          this.scoreboard.getPlayerTeam(this).removePlayer(this)
          this.scoreboard = Bukkit.getScoreboardManager().mainScoreboard
          this.hardClean()
        }
      }
    }
  }

}