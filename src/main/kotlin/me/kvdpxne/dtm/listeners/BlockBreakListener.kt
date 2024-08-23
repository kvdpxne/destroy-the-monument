package me.kvdpxne.dtm.listeners

import me.kvdpxne.dtm.colorize
import me.kvdpxne.dtm.configuration.Configuration
import me.kvdpxne.dtm.game.Arena
import me.kvdpxne.dtm.game.LocalGame
import me.kvdpxne.dtm.game.LocalTeam
import me.kvdpxne.dtm.game.RevivalPosition
import me.kvdpxne.dtm.game.Team
import me.kvdpxne.dtm.game.Teammate
import me.kvdpxne.dtm.scoreboard.updateBlueMonumentCount
import me.kvdpxne.dtm.scoreboard.updateRedMonumentCount
import me.kvdpxne.dtm.shared.ItemsClipboard
import me.kvdpxne.dtm.shared.minecraft.bukkit.cancel
import me.kvdpxne.dtm.shared.minecraft.bukkit.cancelTask
import me.kvdpxne.dtm.shared.minecraft.bukkit.fill
import me.kvdpxne.dtm.shared.minecraft.bukkit.hasInventory
import me.kvdpxne.dtm.shared.minecraft.bukkit.isMonument
import me.kvdpxne.dtm.shared.minecraft.bukkit.isNature
import me.kvdpxne.dtm.shared.minecraft.bukkit.isRich
import me.kvdpxne.dtm.shared.minecraft.bukkit.reset
import me.kvdpxne.dtm.shared.minecraft.bukkit.runSynchronousDelayedTask
import me.kvdpxne.dtm.user.User
import me.kvdpxne.dtm.user.UserManager
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.inventory.ItemStack

/**
 * @since 0.1.0
 */
object BlockBreakListener : Listener {

  /**
   * @since 0.1.0
   */
  private fun updateTeammate(
    team: LocalTeam,
    item: ItemStack
  ) {
    team.teammates.forEach { teammate: Teammate ->

      //
      teammate.currentProfession.ability?.let {
        cancelTask(it.taskIdentifier)
      }

      val player = teammate.user.performer.player!!

      //
      player.reset()
      player.fill(item)

      //
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

  /**
   * @since 0.1.0
   */
  @EventHandler(
    priority = EventPriority.HIGH
  )
  fun handleBlockBreak(
    event: BlockBreakEvent
  ) {
    if (event.isCancelled) {
      return
    }

    // Obiekt gracza, który zniszczył blok.
    val player: Player = event.player

    // Obiekt użytkownika, pozyskany z unikatowego identyfikatora gracza,
    // który zniszczył blok.
    val user: User = UserManager.findByIdentifier(player.uniqueId) ?: return

    // Obiekt lokalnej gry, do której jest przypisany obiekt użytkownika.
    val game: LocalGame = user.game ?: return

    // The game object must have a “started” state
    if (!game.isRunning) {
      return
    }

    // The currently assigned arena object for the game
    val arena: Arena = game.currentArena ?: return

    // The arena object must have a map loaded.
    if (false == arena.map?.isLoaded) {
      return
    }

    // The position object of the destroyed block
    val location = event.block.location

    // The position object of the destroyed block must not have a different map
    // than the object of the currently loaded game arena
    if (arena.map?.world != location.world) {
      return
    }

    //
    for (revivalPosition: RevivalPosition<*> in arena.revivalPositions) {
      if (!revivalPosition.isNear(
          location.x,
          location.y,
          location.z,
          Configuration.RADIUS_OF_BLOCK_INTERACTION
        )
      ) {
        continue
      }
      event.cancel()
      user.sendMessage { configuration: Configuration ->
        configuration.SPAWN_BLOCK_BREAK_DENIED_MESSAGE
      }
      return
    }

    // The object of the destroyed block
    val block: Block = event.block

    //
    if (block.hasInventory() || block.isRich() || block.isNature()) {
      this.disappearBlock(event)
      return
    }

    //
    val x: Int = location.blockX
    val y: Int = location.blockY
    val z: Int = location.blockZ

    if (Material.GRASS == block.type || Material.DIRT == block.type || Material.SOUL_SAND == block.type) {

      //
      val upperBlock: Block = location.world.getBlockAt(x, y + 1, z)

      //
      if (upperBlock.isNature()) {
        upperBlock.type = Material.AIR
      }
    }

    //
    if (!block.isMonument()) {
      return
    }

    // A monument that was destroyed by the user
    val monument = arena.getMonumentPosition(x, y, z) ?: return

    //
    if (monument.isDestroyed) {
      return
    }

    //
    val victimTeam: Team = user.team ?: return

    //
    val teammate: Teammate = user.teammate ?: return

    //
    val monumentBelongs = monument.team

    //
    if (monumentBelongs == victimTeam) {
      event.cancel()
      user.sendMessage("&6&lDTM &7> &fNie możesz zniszczyć monumentu swojej drużyny.")
      return
    }

    //
    this.disappearBlock(event)

    // The team to which the destroyed monument belonged
    val attackedTeam = game.findTeamByIdentifier(monumentBelongs.identifier) ?: return

    //
    if (!attackedTeam.injure()) {
      throw IllegalStateException(
        """
        Unexpectedly the health of the team: \"$attackedTeam\" is less than 0
        and the game has not been completed.
        """.trimIndent()
      )
    }

    // Marks an attacked monument as destroyed
    monument.destroy()

    // Adds one destroyed monument to a teammate's statistics
    teammate.addDestroyedMonument()

    for (team: LocalTeam in game.teams) {
      if (attackedTeam == monumentBelongs) {
        for (teammate: Teammate in team.teammates) {
          updateRedMonumentCount(teammate.fastBoard, attackedTeam.health)
        }
        continue
      }

      for (teammate: Teammate in team.teammates) {
        updateBlueMonumentCount(teammate.fastBoard, attackedTeam.health)
      }
    }

    if (0 < attackedTeam.health) {
      val coloredUser = "${attackedTeam.colorInChat}${user.name}"
      val coloredMonument = "${monumentBelongs.colorInChat}&l${monumentBelongs.name}".colorize().uppercase()

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
        this.updateTeammate(it, ItemsClipboard.LOSE)
        return@forEach
      }

      this.updateTeammate(it, ItemsClipboard.WON)
    }

    // Creates and registers a synchronous delayed game completion task
    runSynchronousDelayedTask(20 * 20L) {
      game.stop()
    }

    // Cancels the task of the game arena timer
    cancelTask(game.timerTaskIdentifier)

    game.sendMessages(
      "",
      "&6&lDTM &7> &fGra została zakończona.",
      "&6&lDTM &7> &fZa &620 &fsekund zostaniesz przeniesiony do poczekalni.",
    )
  }
}