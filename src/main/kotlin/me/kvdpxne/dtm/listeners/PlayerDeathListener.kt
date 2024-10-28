package me.kvdpxne.dtm.listeners

import me.kvdpxne.dtm.arena.Arena
import me.kvdpxne.dtm.arena.ArenaException
import me.kvdpxne.dtm.colorize
import me.kvdpxne.dtm.configuration.Configuration
import me.kvdpxne.dtm.game.LocalGame
import me.kvdpxne.dtm.scoreboard.updateCoinCount
import me.kvdpxne.dtm.scoreboard.updateDeathCount
import me.kvdpxne.dtm.scoreboard.updateKillCount
import me.kvdpxne.dtm.shared.minecraft.bukkit.localUser
import me.kvdpxne.dtm.shared.minecraft.bukkit.respawn
import me.kvdpxne.dtm.shared.minecraft.bukkit.runSynchronousDelayedTask
import me.kvdpxne.dtm.team.Teammate
import me.kvdpxne.dtm.user.LocalUser
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent

object PlayerDeathListener : Listener {

  /**
   * @since 0.1.0
   */
  private fun formatTeammate(
    teammate: Teammate
  ): String {
    val profession = teammate.currentProfession

    val professionName = profession.displayName
    val teammateName = teammate.user.name

    val teamColor = teammate.team
    val professionColor = teamColor.professionColor
    val teammateColor = teamColor.colorInChat

    return "$professionColor&l$professionName $teammateColor$teammateName&r"
  }

  /**
   * @since 0.1.0
   */
  private fun createTeammateSuicideMessage(
    teammate: Teammate
  ): String {
    return "${this.formatTeammate(teammate)} &6umarł".colorize
  }

  /**
   * @since 0.1.0
   */
  private fun createTeammateKillMessage(
    victim: Teammate,
    killer: Teammate
  ): String {
    return "${this.formatTeammate(killer)} &6--> ${this.formatTeammate(victim)}"
      .colorize
  }

  /**
   * @since 0.1.0
   */
  private fun addAndUpdateKills(teammate: Teammate) {
    teammate.addKill()
    updateKillCount(teammate.fastBoard, teammate.statistics.kills)
  }

  /**
   * @since 0.1.0
   */
  private fun addAndUpdateDeaths(teammate: Teammate) {
    teammate.addDeath()
    updateDeathCount(teammate.fastBoard, teammate.statistics.deaths)
  }

  /**
   * @since 0.1.0
   */
  @EventHandler(
    priority = EventPriority.HIGHEST
  )
  fun handlePlayerDeath(
    event: PlayerDeathEvent
  ) {
    // Obiekt gracza, który umarł
    val victim: Player = event.entity

    // Obiekt użytkownika uzyskany na podstawie unikatowego identyfikatora
    // obiektu gracza, który umarł.
    val victimUser: LocalUser = event.entity.localUser ?: return

    // Obiekt lokalnej gry, do której jest przypisany obiekt użytkownika,
    // który umarł.
    val game: LocalGame = victimUser.game ?: return

    // Jeżeli obiekt lokalnej gry nie ma stanu "URUCHOMIONEJ" to plugin nie
    // powinien ingerować w wydarzenie śmierci.
    if (!game.isRunning) {
      return
    }

    // Obiekt areny, na której odbywa się rozgrywka w obiekcie lokalnej gry.
    val arena: Arena = game.currentArena ?: return

    //
    if (false == arena.map?.isLoaded) {
      throw ArenaException(
        """
          Object of LocalGame: $game has a RUNNING stat when the map arena
          $arena is not loaded.
        """.trimIndent()
      )
    }

    //
    if (!game.isInTeam(victimUser) || !game.isInArena(victimUser)) {
      return
    }

    // Usuwa wszelkie byty, które może upuścić gracz po swojej śmierci.
    event.drops.clear()
    event.droppedExp = 0

    // Uruchamia opóźnione o X milisekund synchroniczne zadanie odrodzenia
    // obiektu gracza, który umarł.
    runSynchronousDelayedTask(Configuration.REVIVAL_PLAYER_DELAY) {
      victim.respawn()
    }

    // Obiekt drużynowego uzyskany na podstawie użytkownika, który jest
    // przypisany do obiektu lokalnej gry.
    val victimTeammate: Teammate = game.findTeammateByHostage(victimUser)
      ?: return

    // Anuluje czas odnowienie umiejętności obecnej profesji, która jest
    // przypisana do drużynowego, który zginął.
    victimTeammate.currentProfession.ability?.cancelCooldown()

    // Jeżeli obiekt gracza, który jest zabójcą nie istnieje to obiekt gracza,
    // który jest ofiarą popełnij samobójstwo.
    if (null == victim.killer) {
      // VICTIM_KIT_NAME VICTIM_USER_NAME ACTION
      // Zwiadowca       currant          zginął
      event.deathMessage = this.createTeammateSuicideMessage(victimTeammate)

      //
      this.addAndUpdateDeaths(victimTeammate)
      return
    }

    //
    val killerUser: LocalUser = event.entity.killer.localUser ?: return

    //
    val killerTeammate: Teammate = game.findTeammateByHostage(killerUser)
      ?: return

    // MURDER_KIT_NAME MURDER_USER_NAME ACTION VICTIM_KIT_NAME VICTIM_USER_NAME
    // Zwiadowca       currant          -->    Łucznik         strawberry
    event.deathMessage = this.createTeammateKillMessage(
      victimTeammate,
      killerTeammate
    )

    killerUser.wallet.addCoins(20)
    updateCoinCount(killerTeammate.fastBoard, killerUser.wallet.coins)

    this.addAndUpdateDeaths(victimTeammate)
    this.addAndUpdateKills(killerTeammate)
  }
}