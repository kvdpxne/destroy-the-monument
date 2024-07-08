package me.kvdpxne.dtm.gui

import me.kvdpxne.dtm.colorize
import me.kvdpxne.dtm.colorizeAll
import me.kvdpxne.dtm.game.Game
import me.kvdpxne.dtm.game.GameManager
import me.kvdpxne.dtm.profession.ProfessionManager
import me.kvdpxne.dtm.user.User
import org.bukkit.Material
import org.bukkit.entity.Player
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
      setItem(index, ItemStack(Material.STAINED_CLAY).apply {
        itemMeta = itemMeta.apply {
          val hostagesCount = game.hostages.size
          displayName = "&7> &f${game.name} &6$hostagesCount/unlimited".colorize()
          lore = arrayOf(
            "&7Join the game lobby to be able to",
            "&7interact in the game.",
            "",
            "&7Current map: &6unknown", // TODO add selected map name
            "&8Uid: $key"
          ).colorizeAll()
        }
        durability = 5
      }) { event ->
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

fun createProfessionSelectionGui(user: User) = Gui("Choose your profession", Rows.TWO).apply {
  val item = ItemStack(Material.STAINED_CLAY)
  ProfessionManager.forEachIndexed { index, profession ->

    if (user.profession == profession) {
      item.itemMeta = item.itemMeta.apply {
        displayName = "&a&lWYBRANO".colorize()
      }
      item.durability = 5
    } else {
      val meta = item.itemMeta
      meta.displayName = " "
      item.itemMeta = meta
      item.durability = 4
    }

    setItem(index, item)

    val professionItemIcon = profession.icon
    val meta = professionItemIcon.itemMeta

    meta.displayName = "&7${profession.displayName}".colorize()
    professionItemIcon.itemMeta = meta

    setItem(9 + index, professionItemIcon) { event ->
      user.profession = profession

      with(event.whoClicked as Player) {
        event.isCancelled = true

        closeInventory()
        sendMessage("The ${profession.displayName} class was selected.")
      }

      val game = GameManager.findByUser(user) ?: return@setItem
      val team = game.findTeam(user) ?: return@setItem
      val teammate = team.findTeammate(user) ?: return@setItem

      teammate.professionQueuingPair.apply {
        if (this.current != profession) {
          this.next = profession
        }
      }
    }
  }
}