package me.kvdpxne.dtm.gui

import me.kvdpxne.dtm.game.Game
import me.kvdpxne.dtm.game.GameManager
import me.kvdpxne.dtm.game.LocalGame
import me.kvdpxne.dtm.profession.ProfessionManager
import me.kvdpxne.dtm.shared.ItemsClipboard
import me.kvdpxne.dtm.shared.minecraft.bukkit.ItemBuilder
import me.kvdpxne.dtm.shared.minecraft.bukkit.equipB
import me.kvdpxne.dtm.shared.minecraft.bukkit.reset
import me.kvdpxne.dtm.shared.minecraft.bukkit.toBuilder
import me.kvdpxne.dtm.team.LocalTeam
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

  //
  for (team: LocalTeam in teams) {

    val name = team.name
    val color = team.colorInChat

    gui.setItem(
      iterator.next(),
      Material.WOOL.toBuilder()
        .generation(team.dyeColor.woolData.toInt())
        .name {
          "$color&l$name &8| &6${team.size}/bez limitu"
        }
        .lore(
          "&7Zostaniesz dodany bezpośrednio",
          "&7do drużyny $color&l$name&7."
        )
        .build()
    ) { event: InventoryClickEvent ->
      //
      game.addTeammate(team, user)

      game.sendMessage {
        val displayName = user.performer.player?.displayName
        "&6&lDTM &7> &fGracz &6$displayName &fdołączył do drużyny $color&l$name&f."
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

    game.addTeammate(team, user)

    val player = event.whoClicked as Player
    player.closeInventory()

    game.sendConfiguredMessage {
      val displayName = player.displayName
      val teamColor = team.colorInChat

      val teamName = if (team.name.equals("blue", true)) {
        "Niebieskich"
      } else {
        "Czerwonych"
      }

      "&6&lDTM &7> &fGracz &6$displayName &fdołączył do drużyny $teamColor&l$teamName&f."
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

      player.equipB()

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
        .name("&7${profession.displayName}")
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