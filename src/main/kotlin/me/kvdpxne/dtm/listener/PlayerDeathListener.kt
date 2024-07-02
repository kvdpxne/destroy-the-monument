package me.kvdpxne.dtm.listener

import me.kvdpxne.dtm.colorize
import me.kvdpxne.dtm.game.DefaultTeamColor
import me.kvdpxne.dtm.game.GameManager
import me.kvdpxne.dtm.game.Teammate
import me.kvdpxne.dtm.scoreboard.updateDeathCount
import me.kvdpxne.dtm.scoreboard.updateKillCount
import me.kvdpxne.dtm.user.UserManager
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent

object PlayerDeathListener : Listener {

  private fun formatTeammate(
    teammate: Teammate
  ): String {
    val profession = teammate.professionQueuingPair.current

    profession.ability?.let {
      if (0 > it.taskIdentifier) {
        return@let
      }

      Bukkit.getScheduler().cancelTask(it.taskIdentifier)
    }

    val professionName = profession.displayName
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
      // VICTIM_KIT_NAME VICTIM_USER_NAME ACTION
      // Zwiadowca       currant          zginął
      event.deathMessage = "${this.formatTeammate(victimTeammate)} &6died".colorize()

      victimUser.statistics.addDeaths()
      victimTeammate.statistics.addDeaths()

      victimTeammate.fastBoard?.let {
        updateDeathCount(it, victimTeammate.statistics.deaths)
      }

      return
    }

    val murderUser = UserManager.findByIdentifier(murder.uniqueId)!!
    val murderTeammate = game.findTeam(murderUser)!!.findTeammate(murderUser)!!

    // MURDER_KIT_NAME MURDER_USER_NAME ACTION VICTIM_KIT_NAME VICTIM_USER_NAME
    // Zwiadowca       currant          -->    Łucznik         strawberry
    event.deathMessage = "${this.formatTeammate(murderTeammate)} &6--> ${this.formatTeammate(victimTeammate)}".colorize()

    murderUser.statistics.addKills()
    murderTeammate.statistics.addKills()

    murderTeammate.fastBoard?.let {
      updateKillCount(it, victimTeammate.statistics.kills)
    }

    victimUser.statistics.addDeaths()
    victimTeammate.statistics.addDeaths()

    victimTeammate.fastBoard?.let {
      updateDeathCount(it, victimTeammate.statistics.deaths)
    }
  }
}