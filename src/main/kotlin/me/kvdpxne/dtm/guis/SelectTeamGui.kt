package me.kvdpxne.dtm.guis

import me.kvdpxne.dtm.game.LocalGame
import me.kvdpxne.dtm.gui.Gui
import me.kvdpxne.dtm.gui.GuiArrangement
import me.kvdpxne.dtm.gui.Rows
import me.kvdpxne.dtm.shared.event.cancel
import me.kvdpxne.dtm.shared.item.ItemBuilder
import me.kvdpxne.dtm.shared.item.ItemsClipboard
import me.kvdpxne.dtm.shared.material.toBuilder
import me.kvdpxne.dtm.shared.player.equipItemsOf
import me.kvdpxne.dtm.team.LocalTeam
import me.kvdpxne.dtm.team.Teammate
import me.kvdpxne.dtm.translation.TranslationService
import me.kvdpxne.dtm.translation.message.EnumTranslationKey
import me.kvdpxne.dtm.user.LocalUser
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent

fun createTeamSelectionGui(
  game: LocalGame,
  user: LocalUser
): Gui {

  //
  val gui = Gui(
    TranslationService
      .findLocalMessagesOrDefault(user.locale)
      .findRawMessage(EnumTranslationKey.GUI_SELECT_TEAM.messageKey),
    Rows.ONE
  )

  //
  val teams: Collection<LocalTeam> = game.teams

  //
  check(!teams.isEmpty()) {
    "There is no teams."
  }

  //
  val arrangement: IntArray = GuiArrangement.SINGLE

  //
  check(teams.size <= arrangement.size) {
    "There is more than one team selected."
  }

  //
  val iterator: IntIterator = arrangement.iterator()

  val itemBuilder: ItemBuilder = Material.WOOL.toBuilder()

  val teammate: Teammate? = game.findTeammateByHostage(user)

  //
  for (team: LocalTeam in teams) {

    val color = team.colorInChat

    // TODO delete after adding translations
    // nazwa     : Niebiescy   v Czerwoni
    // do drużyny: Niebieskich v Czerwonych
    // dodany do : Niebieskich v Czerwonych
    val polishTeamName = if (team.name.equals("blue", true)) {
      "Niebieskich"
    } else {
      "Czerwonych"
    }

    gui.setItem(
      iterator.next(),
      itemBuilder
        .generation(team.dyeColor.woolData.toInt())
        .name {
          // TODO delete after adding translations
          val polishTitle = if (team.name.equals("blue", true)) {
            "Niebiescy"
          } else {
            "Czerwoni"
          }

          "$color&l$polishTitle &8| &6${team.size}/bez limitu"
        }
        .lore(
          "&7Zostaniesz dodany bezpośrednio",
          "&7do drużyny $color&l$polishTeamName&7."
        )
        .build()
    ) { event: InventoryClickEvent ->
      if (null != teammate) {
        if (!game.relocateTeammateToTeam(teammate, team)) {
          event.cancel()
          user.sendMessage("&6&lDTM &7> &cNie możesz dołączyć do drużyny, w której już jesteś.")
          return@setItem
        }

        user.sendMessage("&6&lDTM &7> &7Pomyślnie zmieniłeś swoją drużynę.")
        game.sendMessage {
          val displayName = user.performer.player?.displayName
          "&6&lDTM &7> &fGracz &6$displayName &fdołączył do drużyny $color&l$polishTeamName&f."
        }
        event.whoClicked.closeInventory()
        return@setItem
      }

      //
      if (!game.addTeammate(team, user)) {
        event.cancel()
        user.sendMessages(
          arrayOf(
          "&6&lDTM &7> &cNie możesz dołączyć do drużyny.",
          "&6&lDTM &7> &cPrawdopodobnie jest to błąd, który nie powinien nigdy",
          "&cwystąpić."
          )
        )
        event.whoClicked.closeInventory()
        return@setItem
      }

      game.sendMessage {
        val displayName = user.performer.player?.displayName
        "&6&lDTM &7> &fGracz &6$displayName &fdołączył do drużyny $color&l$polishTeamName&f."
      }

      if (!game.isRunning) {
        event.whoClicked.inventory.setItem(8, ItemsClipboard.TEAM_LEAVE_ITEM)
      }

      event.whoClicked.closeInventory()
    }
  }

  gui.setItem(
    4,
    ItemsClipboard.ITEM_TEAM_SELECT_RANDOM
  ) { event: InventoryClickEvent ->
    //
    val team = if (game.isTeamsSameSize) {
      game.randomTeam
    } else {
      game.smallestTeam
    }

    (event.whoClicked as Player).equipItemsOf()

    val color = team.colorInChat

    val polishTeamName = if (team.name.equals("blue", true)) {
      "Niebieskich"
    } else {
      "Czerwonych"
    }

    val displayName = user.performer.player?.displayName

    if (null != teammate) {
      if (!game.relocateTeammateToTeam(teammate, team)) {
        // Ten blok kodu nie powinien/nie ma prawa zostać nigdy wykonany.
        event.cancel()
        user.sendMessage("&6&lDTM &7> &cNie możesz dołączyć do drużyny, w której już jesteś.")
        return@setItem
      }

      user.sendMessage("&6&lDTM &7> &7Pomyślnie zmieniłeś swoją drużynę.")
      game.sendMessage("&6&lDTM &7> &fGracz &6$displayName &fdołączył do drużyny $color&l$polishTeamName&f.")
      event.whoClicked.closeInventory()
      return@setItem
    }

    if (!game.addTeammate(team, user)) {
      event.cancel()
      user.sendMessages(
        arrayOf(
        "&6&lDTM &7> &cNie możesz dołączyć do drużyny.",
        "&6&lDTM &7> &cPrawdopodobnie jest to błąd, który nie powinien nigdy",
        "&cwystąpić."
        )
      )
      event.whoClicked.closeInventory()
      return@setItem
    }

    val player = event.whoClicked as Player
    player.closeInventory()

    game.sendMessage {
      "&6&lDTM &7> &fGracz &6$displayName &fdołączył do drużyny $color&l$polishTeamName&f."
    }
  }

  return gui
}