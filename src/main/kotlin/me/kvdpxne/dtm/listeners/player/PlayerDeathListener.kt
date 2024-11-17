package me.kvdpxne.dtm.listeners.player

import me.kvdpxne.dtm.arena.Arena
import me.kvdpxne.dtm.arena.ArenaException
import me.kvdpxne.dtm.shared.text.colorize
import me.kvdpxne.dtm.configuration.GeneralConfiguration
import me.kvdpxne.dtm.damage.Damage
import me.kvdpxne.dtm.damage.DamageManager
import me.kvdpxne.dtm.damage.DamageOwner
import me.kvdpxne.dtm.game.LocalGame
import me.kvdpxne.dtm.scoreboard.updateCoinCount
import me.kvdpxne.dtm.scoreboard.updateDeathCount
import me.kvdpxne.dtm.scoreboard.updateKillCount
import me.kvdpxne.dtm.shared.player.localUser
import me.kvdpxne.dtm.shared.player.respawn
import me.kvdpxne.dtm.shared.task.runSynchronousDelayedTask
import me.kvdpxne.dtm.shared.world.WorldsHolder
import me.kvdpxne.dtm.team.Teammate
import me.kvdpxne.dtm.user.LocalUser
import me.kvdpxne.dtm.user.LocalUserManager
import org.bukkit.World
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
    return "${formatTeammate(teammate)} &6umarł".colorize
  }

  /**
   * @since 0.1.0
   */
  private fun createTeammateKillMessage(
    victim: Teammate,
    killer: Teammate
  ): String {
    return "${formatTeammate(killer)} &6--> ${formatTeammate(victim)}"
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

  fun handleAssists(
    victim: LocalUser,
    damageOwner: DamageOwner,
  ) {
    val iterator: MutableIterator<Damage> = damageOwner.history.iterator()

    while (iterator.hasNext()) {
      val damage: Damage = iterator.next()
      val attacker: LocalUser? = LocalUserManager.findUserByIdentifierOrNull(damage.attacker)

      if (null == attacker) {
        iterator.remove()
        continue
      }

      attacker.statistics.addAssists()
      attacker.teammate?.statistics?.addAssists()
    }

    //
    damageOwner.removeDamages()
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
    val world: World = event.entity.world
    val lobbyWorld: World? = WorldsHolder.lobbyWorld

    if (world == lobbyWorld) {
      event.keepInventory = true
      event.droppedExp = 0
      return
    }

    // Obiekt gracza, który umarł
    val victim: Player = event.entity

    // Obiekt użytkownika uzyskany na podstawie unikatowego identyfikatora
    // obiektu gracza, który umarł.
    val victimUser: LocalUser = event.entity.localUser

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
    runSynchronousDelayedTask(GeneralConfiguration.REVIVAL_PLAYER_DELAY) {
      victim.respawn()
    }

    // Obiekt drużynowego uzyskany na podstawie użytkownika, który jest
    // przypisany do obiektu lokalnej gry.
    val victimTeammate: Teammate = game.findTeammateByHostage(victimUser)
      ?: return

    // Anuluje czas odnowienie umiejętności obecnej profesji, która jest
    // przypisana do drużynowego, który zginął.
    victimTeammate.currentProfession.ability?.cancelCooldown()

    //
    val damageOwner: DamageOwner = DamageManager.computeDamageOwnerIfAbsent(victim.uniqueId)

    // Jeżeli obiekt gracza, który jest zabójcą nie istnieje to obiekt gracza,
    // który jest ofiarą popełnij samobójstwo.
    if (null == victim.killer) {
      // VICTIM_KIT_NAME VICTIM_USER_NAME ACTION
      // Zwiadowca       currant          zginął
      event.deathMessage = createTeammateSuicideMessage(victimTeammate)

      handleAssists(victimUser, damageOwner)

      //
      addAndUpdateDeaths(victimTeammate)
      return
    }

    //
    val killerUser: LocalUser = event.entity.killer.localUser

    //
    val killerTeammate: Teammate = game.findTeammateByHostage(killerUser)
      ?: return

    // MURDER_KIT_NAME MURDER_USER_NAME ACTION VICTIM_KIT_NAME VICTIM_USER_NAME
    // Zwiadowca       currant          -->    Łucznik         strawberry
    event.deathMessage = createTeammateKillMessage(
      victimTeammate,
      killerTeammate
    )

    killerUser.wallet.addCoins(20)
    updateCoinCount(killerTeammate.fastBoard, killerUser.wallet.coins)

    handleAssists(victimUser, damageOwner)

    addAndUpdateDeaths(victimTeammate)
    addAndUpdateKills(killerTeammate)
  }
}