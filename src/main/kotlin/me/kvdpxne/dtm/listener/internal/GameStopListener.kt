package me.kvdpxne.dtm.listener.internal

import me.kvdpxne.dtm.game.GameStopEvent
import me.kvdpxne.dtm.user.UserPerformer
import me.kvdpxne.thrivi.EventHandler
import me.kvdpxne.thrivi.Listenable
import me.kvdpxne.thrivi.StandardEventPriorities

object GameStopListener : Listenable {

  @EventHandler(priority = StandardEventPriorities.HIGH)
  fun handleGameStop(event: GameStopEvent) {
    val game = event.game
    game.teams.forEach { team ->
      team.teammates.forEach { teammate ->
        val performer = teammate.user.performer as UserPerformer
        performer.getPlayer()!!.run {
          this.inventory.also {
            it.clear()
            it.armorContents = arrayOfNulls(it.armorContents.size)
          }
          this.activePotionEffects.forEach {
            removePotionEffect(it.type)
          }
          this.resetMaxHealth()
          this.setHealth(20.0)
          this.fireTicks = 0
        }
      }
    }
  }

}