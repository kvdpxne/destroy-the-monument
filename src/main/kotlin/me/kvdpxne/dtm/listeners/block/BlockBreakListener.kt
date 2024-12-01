package me.kvdpxne.dtm.listeners.block

import me.kvdpxne.dtm.arena.Arena
import me.kvdpxne.dtm.shared.text.colorize
import me.kvdpxne.dtm.configuration.GeneralConfiguration
import me.kvdpxne.dtm.game.LocalGame
import me.kvdpxne.dtm.position.monument.MonumentPosition
import me.kvdpxne.dtm.position.revival.RevivalPosition
import me.kvdpxne.dtm.scoreboard.updateFirstMonumentCounter
import me.kvdpxne.dtm.scoreboard.updateSecondMonumentCounter
import me.kvdpxne.dtm.shared.item.ItemsClipboard
import me.kvdpxne.dtm.shared.event.cancel
import me.kvdpxne.dtm.shared.task.cancelTask
import me.kvdpxne.dtm.shared.block.disappear
import me.kvdpxne.dtm.shared.player.fill
import me.kvdpxne.dtm.shared.block.hasInventory
import me.kvdpxne.dtm.shared.block.isMonument
import me.kvdpxne.dtm.shared.block.isPlant
import me.kvdpxne.dtm.shared.block.isRich
import me.kvdpxne.dtm.shared.player.localUser
import me.kvdpxne.dtm.shared.player.reset
import me.kvdpxne.dtm.shared.task.runSynchronousDelayedTask
import me.kvdpxne.dtm.team.LocalTeam
import me.kvdpxne.dtm.team.Team
import me.kvdpxne.dtm.team.Teammate
import me.kvdpxne.dtm.translation.TranslationService
import me.kvdpxne.dtm.translation.formatter.Formatter
import me.kvdpxne.dtm.translation.message.EnumMessageKey
import me.kvdpxne.dtm.user.LocalUser
import org.bukkit.Location
import org.bukkit.block.Block
import org.bukkit.block.BlockFace
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

    // Obiekt użytkownika, pozyskany z unikatowego identyfikatora gracza,
    // który zniszczył blok.
    val user: LocalUser = event.player.localUser

    // Obiekt lokalnej gry, do której jest przypisany obiekt użytkownika.
    val game: LocalGame = user.game ?: return

    // Obiekt lokalnej gry musi mieć stan DZIAŁAJĄCY i obiekt użytkownika musi
    // być przypisany do jakiejś drużyny w obiekcie lokalnej gry.
    if (!game.isRunning || !game.isInTeam(user)) {
      return
    }

    // Obiekt areny, która jest obecnie przypisana do obiektu lokalnej gry.
    val arena: Arena = game.currentArena ?: return

    // Obiekt areny musi posiadać załadowaną mapę.
    if (false == arena.map?.isLoaded) {
      return
    }

    // Obiekt lokalizacji zniszczonego obiektu bloku.
    val location: Location = event.block.location

    // Obiekt pozycji zniszczonego bloku musi posiadać identyczny obiekt świata
    // co obiekt areny przypisany do obiektu lokalnej gry.
    if (arena.map?.world != location.world) {
      return
    }

    for (revivalPosition: RevivalPosition<*> in arena.revivalPositions) {
      if (revivalPosition.isNear(
          location.x,
          location.y,
          location.z,
          GeneralConfiguration.RADIUS_OF_BLOCK_INTERACTION
        )
      ) {
        event.cancel()

        TranslationService.chains()
          .receiver(user.performer)
          .message(EnumMessageKey.ARENA_MAP_BLOCK_BREAKING_SPAWN)
          .withoutFormat()
          .useChat()
          .send()

        return
      }
    }

    // Obiekt bloku, który został zniszczony.
    val block: Block = event.block

    //
    if (block.hasInventory() || block.isRich() || block.isPlant()) {
      event.cancel()
      block.disappear()
      return
    }

    if (GeneralConfiguration.BLOCK_PLAT_DROPS) {
      // Obiekt bloku, który znajduje się na osi Y + 1 od osi Y obiektu bloku,
      // który został zniszczony.
      val upperBlock: Block = block.getRelative(BlockFace.UP)

      // Jeżeli obiekt bloku jest typu ROŚLINA to po zniszczeniu bloku, na
      // którym rośnie, ROŚLINA zostanie usunięta.
      if (upperBlock.isPlant()) {
        upperBlock.disappear()
      }
    }

    // Jeżeli zniszczony obiekt bloku nie jest typu MONUMENT
    if (!block.isMonument()) {
      return
    }

    // Obiekt pozycji monumentu, pozyskany z osi zniszczonego bloku, który
    // został zniszczony przez gracza.
    val monumentPosition: MonumentPosition<*> = arena.getMonumentPosition(
      location.blockX,
      location.blockY,
      location.blockZ
    ) ?: return

    // Jeżeli obiekt monumentu
    if (monumentPosition.isDestroyed) {
      return
    }

    // Obiekt lokalnej drużyny, która zniszczyła obiekt bloku, który jest
    // monumentem.
    val killerTeam: LocalTeam = game.findTeamByHostage(user) ?: return

    // Obiekt drużyny, do której należy zniszczony przez gracza obiekt bloku,
    // który jest monumentem.
    val monumentBelongs: Team = monumentPosition.team

    //
    if (killerTeam == monumentBelongs) {
      event.cancel()
      TranslationService.chains()
        .receiver(user.performer)
        .message(EnumMessageKey.ARENA_MAP_BLOCK_BREAKING_MONUMENT_SELF)
        .withoutFormat()
        .useChat()
        .send()

      return
    }

    // Przerywa dalsze wykonywanie zdarzenia i usuwa zniszczony blok przed
    // jego faktycznym zniszczeniem.
    event.cancel()
    block.disappear()

    // Obiekt lokalnej drużyny, do której należy zniszczony obiekt bloku, który
    // jest monumentem.
    val victimTeam: LocalTeam = game.findTeamByIdentifier(
      monumentBelongs.identifier
    ) ?: return

    //
    if (!victimTeam.injure()) {
      throw IllegalStateException(
        """
        Unexpectedly the health of the team: \"$victimTeam\" is less than 0
        and the game has not been completed.
        """.trimIndent()
      )
    }

    // Marks an attacked monument as destroyed
    monumentPosition.destroy()

    // Adds one destroyed monument to a teammate's statistics
    killerTeam.getTeammate(user)?.addDestroyedMonument()

    // Update monument counters
    for (team: LocalTeam in game.teams) {
      for (teammate: Teammate in team.teammates) {
        if (victimTeam == monumentBelongs) {
          updateFirstMonumentCounter(teammate.fastBoard, victimTeam.health)
          continue
        }
        updateSecondMonumentCounter(teammate.fastBoard, victimTeam.health)
      }
    }

    game.sendMessages(fun(): Array<String> {
      val coloredUser = "${killerTeam.colorInChat}${user.name}"
      val coloredMonument = "${monumentBelongs.colorInChat}&l${monumentBelongs.name}".colorize.uppercase()

      val end = when (victimTeam.health) {
        1 -> "&fPozostał &61 &fmonument."
        in 2..4 -> "&fPozostały &6${victimTeam.health} &fmonumenty."
        else -> "&fPozostało &6${victimTeam.health} &fmonumentów."
      }

      return arrayOf(
        "",
        "&6&lDTM &7> &fGracz $coloredUser &fzniszczył monument drużyny $coloredMonument",
        "&6&lDTM &7> $end"
      )
    })

    //
    if (0 < victimTeam.health) {
      return
    }

    //
    for (it in game.teams) {
      if (victimTeam == it) {
        updateTeammate(it, ItemsClipboard.LOSE)
        continue
      }

      updateTeammate(it, ItemsClipboard.WON)
    }

    game.setAsEnding()

    // Creates and registers a synchronous delayed game completion task
    runSynchronousDelayedTask(GeneralConfiguration.GAME_END_DELAY * 20L) {
      game.stop()
    }

    // Cancels the task of the game arena timer
    cancelTask(game.timerTaskIdentifier)

    game.prepareMessage(EnumMessageKey.GAME_END)
      .format(
        Formatter.begin(1)
          .with("REMAINING_TIME", GeneralConfiguration.GAME_END_DELAY)
      )
      .useChat()
      .send()
  }
}