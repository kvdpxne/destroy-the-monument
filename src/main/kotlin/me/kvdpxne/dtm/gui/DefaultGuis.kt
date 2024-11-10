package me.kvdpxne.dtm.gui

import me.kvdpxne.dtm.game.Game
import me.kvdpxne.dtm.game.GameManager
import me.kvdpxne.dtm.game.LocalGame
import me.kvdpxne.dtm.profession.ProfessionManager
import me.kvdpxne.dtm.shared.event.cancel
import me.kvdpxne.dtm.shared.item.ItemBuilder
import me.kvdpxne.dtm.shared.item.ItemsClipboard
import me.kvdpxne.dtm.shared.item.toBuilder
import me.kvdpxne.dtm.shared.material.toBuilder
import me.kvdpxne.dtm.shared.player.equipItemsOf
import me.kvdpxne.dtm.shared.player.equipItemsOfTeamSelection
import me.kvdpxne.dtm.shared.player.reset
import me.kvdpxne.dtm.team.LocalTeam
import me.kvdpxne.dtm.team.Teammate
import me.kvdpxne.dtm.user.LocalUser
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack

fun createTeamSelectionGui(
  game: LocalGame,
  user: LocalUser
): Gui {

  //
  val gui = Gui("Wybierz drużynę", Rows.ONE)

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
          "&6&lDTM &7> &cNie możesz dołączyć do drużyny.",
          "&6&lDTM &7> &cPrawdopodobnie jest to błąd, który nie powinien nigdy",
          "&cwystąpić."
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
        "&6&lDTM &7> &cNie możesz dołączyć do drużyny.",
        "&6&lDTM &7> &cPrawdopodobnie jest to błąd, który nie powinien nigdy",
        "&cwystąpić."
      )
      event.whoClicked.closeInventory()
      return@setItem
    }

    val player = event.whoClicked as Player
    player.closeInventory()

    game.sendConfiguredMessage {
      "&6&lDTM &7> &fGracz &6$displayName &fdołączył do drużyny $color&l$polishTeamName&f."
    }
  }

  return gui
}

fun createGameSelectionGui(user: LocalUser): Gui {
  // Lista dostępnych obiektów gier
  val games: List<Game<*>> = GameManager.games

  //
  val gui: Gui = Gui.withDecimal(
    "Game selection",
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

fun createProfessionSelectionGui(user: LocalUser): Gui {

  val gui = Gui("Choose your profession", Rows.TWO)
  val itemBuilder = Material.STAINED_CLAY.toBuilder()

  ProfessionManager.professions.forEachIndexed { index, profession ->

    gui.setItem(
      index, if (user.currentProfession == profession) {
        itemBuilder.generation(5)
          .name("&a&lWYBRANO")
          .build()
      } else if (!profession.enabled) {
        itemBuilder.generation(14)
          .name("&c&lNIEDOSTĘPNA")
          .build()
      } else {
        itemBuilder.generation(4)
          .name("&6&lDOSTĘPNA")
          .build()
      }
    )

    gui.setItem(
      9 + index,
      profession.icon.toBuilder()
        .name("&e&l${profession.displayName}")
        .clearAttributes()
        .build()
    ) { event: InventoryClickEvent ->
      if (!profession.enabled) {
        user.sendMessage("&6&lDTM &7> &cProfesja jest obecnie wyłączona lub niedostępna.")
        return@setItem
      }

      //
      user.updateCurrentProfession(profession.clone())

      event.isCancelled = true
      event.whoClicked.closeInventory()

      user.sendMessage("&6&lDTM &7> &fProfesja &a&l${profession.displayName} &fzostała wybrana.")

      val teammate = user.teammate ?: return@setItem

      if (teammate.currentProfession == profession) {
        return@setItem
      }

      teammate.addProfession(profession.clone())
      teammate.sendMessage("&6&lDTM &7> &fProfesja zostanie zmieniona po śmierci.")
    }
  }

  return gui
}