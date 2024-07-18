package me.kvdpxne.dtm.gui

import me.kvdpxne.dtm.colorize
import me.kvdpxne.dtm.colorizeAll
import me.kvdpxne.dtm.game.Game
import me.kvdpxne.dtm.game.GameManager
import me.kvdpxne.dtm.profession.ProfessionManager
import me.kvdpxne.dtm.shared.toBuilder
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
      displayName = "&c&lRED &r&8| &6$teamSize/unlimited".colorize()
      lore = arrayOf(
        "&7You will be added directly to",
        "&7the &cRED &7team."
      ).colorizeAll()
    }
  }) {
    game.addTeammate(red) { user }
    with(it.whoClicked as Player) {
      closeInventory()
      game.sendMessage("&7> &f$displayName &7joined the &c&lRED &7team.")
    }
  }

  val blue = iterator.next().identity
  setItem(8, coloredWool.apply {
    durability = 11
    itemMeta = itemMeta.apply {
      val teamSize = game.findTeam(blue)?.size() ?: 0
      displayName = "&9&lBLUE &r&8| &6$teamSize/unlimited".colorize()
      lore = arrayOf(
        "&7You will be added directly to",
        "&7the &9BLUE &7team."
      ).colorizeAll()
    }
  }) {
    game.addTeammate(blue) { user }
    with(it.whoClicked as Player) {
      closeInventory()
      game.sendMessage("&7> &f$displayName &7joined the &9&lBLUE &7team.")
    }
  }

  setItem(4, ItemStack(Material.OBSIDIAN).apply {
    itemMeta = itemMeta.apply {
      displayName = "&6Join the Game".colorize()
      lore = listOf(
        "&7You will be added to a team that",
        "&7currently has fewer players."
      ).colorizeAll()
    }
  }) {
    val name = if (game.allTeamsAreSameSize()) {
      game.teams.random().identity
    } else {
      game.findSmallerTeam()!!.identity
    }

    game.addTeammate(name) { user }
    with(it.whoClicked as Player) {
      closeInventory()

      game.sendMessage("&7> &f$displayName &7joined the ${name.colorInChat}&l${name.name.uppercase()} &7team.")
    }
  }
}

fun createGameSelectionGui(user: User) = GameManager.games.let {
  Gui("Game selection", Rows.findRowBySize(it.size)).apply {
    it.onEachIndexed { index, (key, game) ->

      setItem(index, Material.STAINED_CLAY.toBuilder()
        .damage(5)
        .name {
          val name = game.name
          val hostagesCount = game.hostages.size

          "&7> &f$name &6$hostagesCount/unlimited"
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
        with(event.whoClicked as Player) {
          closeInventory()
          sendMessage("You have been added to the ${game.name} game.")

          createTeamSelectionGui(game, user).open(this)
        }
      }
    }
  }
}

fun createProfessionSelectionGui(user: User): Gui {

  val gui = Gui("Choose your profession", Rows.TWO)
  val itemBuilder = Material.STAINED_CLAY.toBuilder()

  ProfessionManager.professions.forEachIndexed { index, profession ->

    gui.setItem(index, if (user.currentProfession == profession) {
      itemBuilder.damage(5)
        .name("&a&lWYBRANO")
        .build()
    } else if (!profession.enabled) {
      itemBuilder.damage(14)
        .name("&c&lNIEDOSTĘPNA")
        .build()
    } else {
      itemBuilder.damage(4)
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