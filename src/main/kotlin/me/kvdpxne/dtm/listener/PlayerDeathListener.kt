package me.kvdpxne.dtm.listener

import me.kvdpxne.dtm.colorize
import me.kvdpxne.dtm.game.DefaultTeamColor
import me.kvdpxne.dtm.game.GameManager
import me.kvdpxne.dtm.game.Team
import me.kvdpxne.dtm.user.User
import me.kvdpxne.dtm.user.UserManager
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent

object PlayerDeathListener : Listener {

  private fun formatTeammate(
    team: Team,
    user: User
  ): String {
    val professionName = user.profession.displayName ?: return "UNKNOWN"
    val teammateName = user.name

    val teamColor = team.identity as DefaultTeamColor
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

    if (game.isInArenaMap(victimUser).not()) {
      return
    }

    event.drops.clear()
    event.droppedExp = 0

    val victimTeam = game.findTeam(victimUser)!!

    val murder = victim.killer
    if (null == murder) {
      // VICTIM_KIT_NAME VICTIM_USER_NAME ACTION
      // Zwiadowca       currant          zginął
      event.deathMessage = "${this.formatTeammate(victimTeam, victimUser)} &6died".colorize()
      return
    }

    val murderUser = UserManager.findByIdentifier(murder.uniqueId)!!
    val murderTeam = game.findTeam(murderUser)!!

    // MURDER_KIT_NAME MURDER_USER_NAME ACTION VICTIM_KIT_NAME VICTIM_USER_NAME
    // Zwiadowca       currant          -->    Łucznik         strawberry
    event.deathMessage = "${this.formatTeammate(murderTeam, murderUser)} &6--> ${this.formatTeammate(victimTeam, victimUser)}".colorize()
  }
}