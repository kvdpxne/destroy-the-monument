package me.kvdpxne.dtm.listener.internal

import me.kvdpxne.dtm.DestroyTheMonument
import me.kvdpxne.dtm.game.DefaultTeamColor
import me.kvdpxne.dtm.event.GameStartEvent
import me.kvdpxne.dtm.game.toLocation
import me.kvdpxne.dtm.scoreboard.createServerScoreboard
import me.kvdpxne.dtm.scoreboard.createServerTeam
import me.kvdpxne.dtm.scoreboard.initScoreboard
import me.kvdpxne.dtm.shared.fillExpBar
import me.kvdpxne.dtm.shared.hardClean
import me.kvdpxne.dtm.tasks.GameTimeUpdateTaskTimer
import me.kvdpxne.dtm.user.UserPerformer
import me.kvdpxne.thrivi.EventHandler
import me.kvdpxne.thrivi.Listenable
import me.kvdpxne.thrivi.StandardEventPriorities
import org.bukkit.Bukkit

object GameStartListener : Listenable {

  @EventHandler(priority = StandardEventPriorities.HIGH)
  fun handleGameStart(event: GameStartEvent) {
    val game = event.game
    val arena = game.currentArena!!

    //
    val bukkitTeamScoreboard = createServerScoreboard()

    //
    val timeTask = GameTimeUpdateTaskTimer(game)

    game.teams.forEach { team ->

      val bukkitTeam = createServerTeam(
        bukkitTeamScoreboard,
        team.identity.key,
        DefaultTeamColor.findByIdentity(team.identity)!!.chatColor
      )

      val location = arena.spawnPoints[team.identity]?.let {
        val world = arena.map?.world ?: return@forEach
        it.toLocation(world)
      }

      team.teammateMutableSet.forEach { teammate ->
        val profession = teammate.professionQueuingPair.current
        val performer = teammate.user.performer as UserPerformer

        performer.getPlayer()!!.run {
          this.teleport(location)
          this.hardClean()

          scoreboard = bukkitTeamScoreboard
          bukkitTeam.addPlayer(this)

          val fastBoard = initScoreboard(
            this,
            game.teamSizeMutableMap[DefaultTeamColor.RED] ?: 0,
            game.teamHealthMutableMap[DefaultTeamColor.RED] ?: 0,
            game.teamSizeMutableMap[DefaultTeamColor.BLUE] ?: 0,
            game.teamHealthMutableMap[DefaultTeamColor.BLUE] ?: 0,
            1000
          )

          teammate.fastBoard = fastBoard
          timeTask.playerMutableList += fastBoard

          profession.equip(this, (teammate.teamColor as DefaultTeamColor).dyeColor)
          profession.ability?.let {
            if (it.readyAfterDeath) {
              it.markReady()
              it.whenReady(this)

              // Fill player exp bar after 200 ms
              Bukkit.getScheduler().runTaskLaterAsynchronously(
                DestroyTheMonument.instance,
                { this.fillExpBar() },
                4L
              )
              return
            }

            it.run(this, true)
          }
        }
      }
    }

    game.timerTaskIdentifier = timeTask.runTaskTimerAsynchronously(
      DestroyTheMonument.instance,
      20L,
      20L
    ).taskId
  }
}