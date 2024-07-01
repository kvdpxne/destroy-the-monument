package me.kvdpxne.dtm.listener

import me.kvdpxne.dtm.colorize
import me.kvdpxne.dtm.game.DefaultTeamColor
import me.kvdpxne.dtm.game.GameManager
import me.kvdpxne.dtm.game.Teammate
import me.kvdpxne.dtm.user.UserManager
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent

object PlayerDeathListener : Listener {

  private fun formatTeammate(
    teammate: Teammate
  ): String {
    val professionName = teammate.professionQueuingPair.current.displayName
    val teammateName = teammate.user.name

    val teamColor = teammate.teamColor as DefaultTeamColor
    val professionColor = teamColor.professionColor
    val teammateColor = teamColor.chatColor

    return "$professionColor&l$professionName $teammateColor$teammateName&r"
  }

  @EventHandler
  fun handlePlayerDeath(event: PlayerDeathEvent) {
    val victim = event.entity

    // TODO optimization needed
    val victimUser = UserManager.findByIdentifier(victim.uniqueId) ?: return
    val game = GameManager.findByUser(victimUser) ?: return

    if (
      !game.state.isStarted() ||
      null == game.currentArena ||
      !game.isInTeam(victimUser) ||
      !game.isInArenaMap(victimUser)
    ) {
      return
    }

    event.drops.clear()
    event.droppedExp = 0

    //
    val victimTeammate = game.findTeam(victimUser)!!.findTeammate(victimUser)!!

    val murder = victim.killer
    if (null == murder) {
      victimUser.statistics.addDeaths()

      // VICTIM_KIT_NAME VICTIM_USER_NAME ACTION
      // Zwiadowca       currant          zginął
      event.deathMessage = "${this.formatTeammate(victimTeammate)} &6died".colorize()
      return
    }

    val murderUser = UserManager.findByIdentifier(murder.uniqueId)!!
    val murderTeammate = game.findTeam(murderUser)!!.findTeammate(murderUser)!!

    murderUser.statistics.addKills()
    victimUser.statistics.addDeaths()

    // MURDER_KIT_NAME MURDER_USER_NAME ACTION VICTIM_KIT_NAME VICTIM_USER_NAME
    // Zwiadowca       currant          -->    Łucznik         strawberry
    event.deathMessage = "${this.formatTeammate(murderTeammate)} &6--> ${this.formatTeammate(victimTeammate)}".colorize()
  }
}