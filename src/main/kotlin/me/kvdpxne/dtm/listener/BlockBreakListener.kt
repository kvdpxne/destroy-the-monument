package me.kvdpxne.dtm.listener

import me.kvdpxne.dtm.DestroyTheMonument
import me.kvdpxne.dtm.colorize
import me.kvdpxne.dtm.game.GameManager
import me.kvdpxne.dtm.game.Team
import me.kvdpxne.dtm.game.findMonument
import me.kvdpxne.dtm.scoreboard.updateBlueMonumentCount
import me.kvdpxne.dtm.scoreboard.updateRedMonumentCount
import me.kvdpxne.dtm.shared.hardClean
import me.kvdpxne.dtm.shared.isMonument
import me.kvdpxne.dtm.shared.isRich
import me.kvdpxne.dtm.shared.toBuilder
import me.kvdpxne.dtm.tasks.GameStopTaskTimer
import me.kvdpxne.dtm.user.UserManager
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.inventory.ItemStack

private val LOSE = Material.DEAD_BUSH.toBuilder()
  .name("&c&lPRZEGRALES")
  .build()

private val WON = Material.DIAMOND.toBuilder()
  .name("&a&lWYGRALES")
  .build()

object BlockBreakListener : Listener {

  private fun fs(team: Team, item: ItemStack) {
    team.teammates.forEach { teammate ->
      val player = teammate.user.performer.player!!

      player.hardClean()

      player.inventory.also { inventory ->
        repeat(36) { i ->
          inventory.setItem(i, item)
        }
      }

      player.allowFlight = true
      player.isFlying = true
    }
  }

  /**
   * Cancels a block break event and replaces the broken block with air.
   *
   * @param event The `BlockBreakEvent` representing the block being broken.
   */
  private fun disappearBlock(event: BlockBreakEvent) {
    event.isCancelled = true
    event.block.type = Material.AIR
  }

  @EventHandler
  fun handleBlockBreak(event: BlockBreakEvent) {
    if (event.isCancelled) {
      return
    }

    val type = event.block.type

    val isRich = type.isRich()
    val isMonument = type.isMonument()

    if (isRich.not() && isMonument.not()) {
      return
    }

    // A user who destroyed a monument
    val user = UserManager.findByIdentifier(event.player.uniqueId) ?: return

    // A game in which the user destroyed a monument
    val game = GameManager.findByUser(user) ?: return

    // The team to which the user who destroyed the monument is assigned
    val team = game.findTeam(user) ?: return

    //
    val teammate = team.findTeammate(user) ?: return

    // An arena in which the game is played
    val arena = game.currentArena ?: return

    if (game.state.isStarted().not() || game.isInArenaMap(user).not()) {
      return
    }

    if (isRich) {
      this.disappearBlock(event)
      return
    }

    // A monument that was destroyed by the user
    val monument = arena.findMonument(event.block.location) ?: return

    val teamIdentity = team.identity
    val monumentIdentity = monument.team

    if (teamIdentity == monumentIdentity) {
      event.isCancelled = true
      user.sendMessage("&6&lDTM &7> &fNie możesz zniszczyć monumentu swojej drużyny.")
      return
    }

    this.disappearBlock(event)

    // The team to which the destroyed monument belonged
    val attackedTeam = game.findTeam(monumentIdentity) ?: return

    if (!attackedTeam.dealDamage()) {
      // TODO stop game
    }

    //
    teammate.addDestroyedMonument()

    game.teams.forEach {
      if (attackedTeam.identity == monumentIdentity) {
        it.teammates.forEach {
          updateRedMonumentCount(it.fastBoard!!, attackedTeam.health)
        }
        return@forEach
      }

      it.teammates.forEach { teammate ->
        updateBlueMonumentCount(teammate.fastBoard!!, attackedTeam.health)
      }
    }

    if (0 < attackedTeam.health) {
      val coloredUser = "${teamIdentity.colorInChat}${user.name}"
      val coloredMonument = "${monumentIdentity.colorInChat}&l${monumentIdentity.name}".colorize().uppercase()

      val end = when (attackedTeam.health) {
        1 -> "&fPozostał &61 &fmonument."
        in 2..4 -> "&fPozostały &6${attackedTeam.health} &fmonumenty."
        else -> "&fPozostało &6${attackedTeam.health} &fmonumentów."
      }

      game.sendMessages(
        "",
        "&6&lDTM &7> &fGracz $coloredUser &fzniszczył monument drużyny $coloredMonument",
        "&6&lDTM &7> $end"
      )
      return
    }

    game.teams.forEach {
      if (attackedTeam == it) {
        this.fs(it, LOSE)
        return@forEach
      }

      this.fs(it, WON)
    }

    Bukkit.getScheduler().cancelTask(game.timerTaskIdentifier)
    GameStopTaskTimer(game).runTaskLater(
      DestroyTheMonument.instance,
      20 * 20L
    )
    game.sendMessages(
      "",
      "&6&lDTM &7> &fGra została zakończona.",
      "&6&lDTM &7> &fZa &620 &fsekund zostaniesz przeniesiony do poczekalni.",
    )
  }
}