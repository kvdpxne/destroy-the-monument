package me.kvdpxne.dtm.guis

import me.kvdpxne.dtm.game.Game
import me.kvdpxne.dtm.game.GameManager
import me.kvdpxne.dtm.game.LocalGame
import me.kvdpxne.dtm.gui.Gui
import me.kvdpxne.dtm.shared.item.ItemBuilder
import me.kvdpxne.dtm.shared.material.toBuilder
import me.kvdpxne.dtm.shared.player.equipItemsOfTeamSelection
import me.kvdpxne.dtm.shared.player.reset
import me.kvdpxne.dtm.translation.TranslationService
import me.kvdpxne.dtm.translation.message.EnumMessageKey
import me.kvdpxne.dtm.user.LocalUser
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack

fun createGameSelectionGui(user: LocalUser): Gui {
  // An immutable list of available local games.
  val games: List<Game<*>> = GameManager.games

  //
  val gui = Gui.withDecimal(
    TranslationService
      .findLocalMessagesOrDefault(user.locale)
      .findRawMessage(EnumMessageKey.GUI_SELECT_GAME.messageKey),
    games.size
  )

  //
  val itemBuilder: ItemBuilder = Material.STAINED_CLAY.toBuilder()
    .generation(5)

  //
  games.forEachIndexed { index: Int, game: Game<*> ->

    if (game !is LocalGame) {
      return@forEachIndexed
    }

    //
    val item: ItemStack = itemBuilder
      .name {
        val name = game.name
        val hostagesCount = game.numberOfHostages

        "&7> &f$name &6$hostagesCount/bez limitu"
      }
      .lore(
        "&7Join the game lobby to be able to",
        "&7interact in the game.",
        "",
        "&7Current map: &6${game.currentArena?.name ?: "unknown"}",
        "&8Uid: ${game.identifier}"
      )
      .build()

    //
    gui.setItem(index, item) { event: InventoryClickEvent ->
      game.addHostage(user)

      user.sendMessage("&6&lDTM &7> &fDołączyłeś do gry &a${game.name}&f.")

      val player = event.whoClicked as Player
      player.closeInventory()
      player.reset()

      player.equipItemsOfTeamSelection()

      createTeamSelectionGui(game, user).open(player)
    }
  }

  return gui
}