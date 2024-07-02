package me.kvdpxne.dtm.listener

import me.kvdpxne.dtm.DestroyTheMonument
import me.kvdpxne.dtm.game.DefaultTeamColor
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
import me.kvdpxne.dtm.user.UserPerformer
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin

private val LOSE = Material.DEAD_BUSH.toBuilder()
  .name("&c&lPRZEGRALES")
  .build()

private val WON = Material.DIAMOND.toBuilder()
  .name("&a&lWYGRALES")
  .build()

object BlockBreakListener : Listener {

  private fun fs(team: Team, item: ItemStack) {
    team.teammates.forEach { teammate ->
      val player = (teammate.user.performer as UserPerformer).getPlayer()!!

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

    //
    if (monument.destroyed) {
      return
    }

    val teamIdentity = team.identity
    val monumentIdentity = monument.team

    if (teamIdentity == monumentIdentity) {
      event.isCancelled = true
      user.sendMessage("&7You cannot destroy your team's monument!")
      return
    }

    this.disappearBlock(event)

    // The team to which the destroyed monument belonged
    val attackedTeam = game.findTeam(monumentIdentity) ?: return

    attackedTeam.health -= 1

    if (attackedTeam.identity == DefaultTeamColor.RED) {
      game.teams.forEach {
        it.teammates.forEach { teammate ->
          updateRedMonumentCount(teammate.fastBoard!!, attackedTeam.health)
        }
      }
    } else {
      game.teams.forEach {
        it.teammates.forEach { teammate ->
          updateBlueMonumentCount(teammate.fastBoard!!, attackedTeam.health)
        }
      }
    }

    game.sendMessages {
      (teamIdentity as DefaultTeamColor)
      val coloredUser = teamIdentity.chatColor.toString() + user.name

      (monumentIdentity as DefaultTeamColor)
      val coloredMonument = monumentIdentity.chatColor.toString() + monumentIdentity.key

      arrayOf(
        "&7An $coloredUser &7player has destroyed the $coloredMonument &7team monument.",
        "&7There are &6${attackedTeam.health} &7monuments left."
      )
    }

    //
    if (0 < attackedTeam.health) {
      return
    }

    game.teams.forEach {
      if (attackedTeam == it) {
        this.fs(it, LOSE)
        return@forEach
      }

      this.fs(it, WON)
    }

    GameStopTaskTimer(game).runTaskLater(
      DestroyTheMonument.instance,
      20 * 20L
    )
    game.sendMessages(
      "&7The game has ended.",
      "&7In &620 &7seconds you will be moved to the lobby."
    )
  }
}