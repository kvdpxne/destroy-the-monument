package me.kvdpxne.dtm.listener.internal

import me.kvdpxne.dtm.game.DefaultTeamColor
import me.kvdpxne.dtm.game.GameStartEvent
import me.kvdpxne.dtm.game.toLocation
import me.kvdpxne.dtm.user.UserPerformer
import me.kvdpxne.thrivi.EventHandler
import me.kvdpxne.thrivi.Listenable
import me.kvdpxne.thrivi.StandardEventPriorities

object GameStartListener : Listenable {

  @EventHandler(priority = StandardEventPriorities.HIGH)
  fun handleGameStart(event: GameStartEvent) {
    val game = event.game
    val arena = game.currentArena!!
    game.teams.forEach { team ->
      val location = arena.spawnPoints[team.identity]?.let {
        val world = arena.map?.world ?: return@forEach
        it.toLocation(world)
      }
      team.teammates.forEach { teammate ->
        var profession = teammate.profession
        if (null == profession) {
          profession = teammate.user.profession
        }
        val performer = teammate.user.performer as UserPerformer
        performer.getPlayer()!!.run {
          this.teleport(location)
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
          profession.equip(this, (teammate.teamColor as DefaultTeamColor).dyeColor)
          profession.addEffect(this)
        }
      }
    }
  }
}