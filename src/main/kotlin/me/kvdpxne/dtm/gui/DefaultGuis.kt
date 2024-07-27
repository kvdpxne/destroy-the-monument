package me.kvdpxne.dtm.gui

import me.kvdpxne.dtm.colorize
import me.kvdpxne.dtm.colorizeAll
import me.kvdpxne.dtm.game.Game
import me.kvdpxne.dtm.game.GameManager
import me.kvdpxne.dtm.profession.ProfessionManager
import me.kvdpxne.dtm.shared.ItemsClipboard
import me.kvdpxne.dtm.shared.bukkit.reset
import me.kvdpxne.dtm.shared.bukkit.setItem
import me.kvdpxne.dtm.shared.bukkit.toBuilder
import me.kvdpxne.dtm.user.User
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack

fun createTeamSelectionGui(game: Game, user: User) = Gui("Team selection", Rows.ONE).apply {
  val coloredWool = ItemStack(Material.WOOL)

  val iterator = game.teams.iterator()

  val red = iterator.next().identity

  setItem(0, coloredWool.apply {
    durability = 14
    itemMeta = itemMeta.apply {
      val teamSize = game.findTeam(red)?.size() ?: 0
      displayName = "&c&lCzerwoni &r&8| &6$teamSize/bez limitu".colorize()
      lore = arrayOf(
        "&7Zostaniesz dodany bezpośrednio",
        "&7do drużyny &c&lCzerwonych&7."
      ).colorizeAll()
    }
  }) {
    game.addTeammate(red) { user }

    val player = it.whoClicked as Player
    player.closeInventory()

    game.sendMessage {
      val displayName = player.displayName
      "&6&lDTM &7> &fGracz &6$displayName &fdołączył do drużyny &c&lCzerwonych&f."
    }
  }

  val blue = iterator.next().identity
  setItem(8, coloredWool.apply {
    durability = 11
    itemMeta = itemMeta.apply {
      val teamSize = game.findTeam(blue)?.size() ?: 0
      displayName = "&b&lNiebiescy &r&8| &6$teamSize/bez limitu".colorize()
      lore = arrayOf(
        "&7Zostaniesz dodany bezpośrednio",
        "&7do drużyny &b&lNiebieskich&7."
      ).colorizeAll()
    }
  }) {
    game.addTeammate(blue) { user }

    val player = it.whoClicked as Player
    player.closeInventory()

    game.sendMessage {
      val displayName = player.displayName
      "&6&lDTM &7> &fGracz &6$displayName &fdołączył do drużyny &b&lNiebieskich&f."
    }
  }

  setItem(4, ItemsClipboard.ITEM_TEAM_SELECT_RANDOM) {

    val team = if (game.allTeamsAreSameSize()) {
      game.teams.random().identity
    } else {
      game.findSmallerTeam()!!.identity
    }

    game.addTeammate(team) { user }

    val player = it.whoClicked as Player
    player.closeInventory()

    game.sendMessage {
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
}

fun createGameSelectionGui(user: User) = GameManager.games.let {
  Gui("Game selection", Rows.findRowBySize(it.size)).apply {
    it.onEachIndexed { index, (key, game) ->

      setItem(index, Material.STAINED_CLAY.toBuilder()
        .generation(5)
        .name {
          val name = game.name
          val hostagesCount = game.hostages.size

          "&7> &f$name &6$hostagesCount/bez limitu"
        }
        .lore(
          "&7Join the game lobby to be able to",
          "&7interact in the game.",
          "",
          "&7Current map: &6unknown", // TODO add selected map name
          "&8Uid: $key"
        )
        .build()
      ) { event ->
        game.addHostage(user)
        user.sendMessage("&6&lDTM &7> &fDołączyłeś do gry &a${game.name}&f.")

        val player = event.whoClicked as Player
        player.closeInventory()
        player.reset()

        player.setItem(0, ItemsClipboard.ITEM_TEAM_SELECT)
        player.setItem(1, ItemsClipboard.ITEM_PROFESSION_SELECT)
        player.setItem(8, ItemsClipboard.ITEM_GAME_LEAVE)

        createTeamSelectionGui(game, user).open(player)
      }
    }
  }
}

fun createProfessionSelectionGui(user: User): Gui {

  val gui = Gui("Choose your profession", Rows.TWO)
  val itemBuilder = Material.STAINED_CLAY.toBuilder()

  ProfessionManager.professions.forEachIndexed { index, profession ->

    gui.setItem(index, if (user.currentProfession == profession) {
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
    })

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
      user.currentProfession = profession.clone()

      event.isCancelled = true
      event.whoClicked.closeInventory()

      user.sendMessage("&6&lDTM &7> &fProfesja &a&l${profession.displayName} &fzostała wybrana.")

      val game = GameManager.findByUser(user) ?: return@setItem
      val team = game.findTeam(user) ?: return@setItem
      val teammate = team.findTeammate(user) ?: return@setItem

      teammate.professionQueuingPair.apply {
        if (this.current == profession) {
          return@apply
        }

        this.next = profession.clone()
        teammate.sendMessage("&6&lDTM &7> &fProfesja zostanie zmieniona po śmierci.")
      }
    }
  }

  return gui
}