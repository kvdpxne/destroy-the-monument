package me.kvdpxne.dtm.gui

import me.kvdpxne.dtm.game.DefaultTeamColor
import me.kvdpxne.dtm.game.Game
import me.kvdpxne.dtm.game.GameManager
import me.kvdpxne.dtm.game.Team
import me.kvdpxne.dtm.profession.ProfessionManager
import me.kvdpxne.dtm.user.User
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

fun createTeamSelectionGui(game: Game, user: User) = Gui("Wybór drużyny", Rows.ONE).apply {
  val coloredWool = ItemStack(Material.WOOL)

  coloredWool.durability = 14
  coloredWool.apply {
    itemMeta = itemMeta.apply {
      displayName = ChatColor.translateAlternateColorCodes('&', "&c&lCzerwoni")
    }
  }

  setItem(0, coloredWool) {
    game.addTeammate(DefaultTeamColor.RED) {
      user
    }
    with(it.whoClicked as Player) {
      closeInventory()
      sendMessage("You have been added to the red team.")
    }
  }

  setItem(4, ItemStack(Material.OBSIDIAN).apply {
    itemMeta = itemMeta.apply {
      displayName = ChatColor.translateAlternateColorCodes('&', "&7Dołącz do Gry")
      lore = listOf(ChatColor.translateAlternateColorCodes('&', "&6Dołącz do mniejszej drużyny!"))
    }
  }) {
    val name = if (game.allTeamsAreSameSize()) {
      game.teams.random().identity
    } else {
      game.findSmallerTeam()!!.identity
    }

    game.addTeammate(name) {
      user
    }
    with(it.whoClicked as Player) {
      closeInventory()
      sendMessage("You have been added to the ${name.key} team.")
    }
  }

  coloredWool.durability = 11
  coloredWool.apply {
    itemMeta = itemMeta.apply {
      displayName = ChatColor.translateAlternateColorCodes('&', "&b&lNiebiescy")
    }
  }
  setItem(8, coloredWool) {
    game.addTeammate(DefaultTeamColor.BLUE) {
      user
    }
    with(it.whoClicked as Player) {
      closeInventory()
      sendMessage("You have been added to the blue team.")
    }
  }
}

fun createGameSelectionGui(user: User) = GameManager.games.let {
  Gui("Wybierz Gre", Rows.findRowBySize(it.size)).apply {
    it.onEachIndexed { index, (key, game) ->
      setItem(index, ItemStack(Material.STAINED_CLAY).apply {
        itemMeta = itemMeta.apply {
          displayName = game.name
          lore = listOf(key.toString())
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

fun createProfessionSelectionGui(user: User) = Gui("Wybór klasy", Rows.TWO).apply {
  val item = ItemStack(Material.STAINED_CLAY)
  ProfessionManager.forEachIndexed { index, profession ->
    if (user.profession == profession) {
      item.durability = 5
    } else {
      item.durability = 4
    }
    setItem(index, item)
    setItem(9 + index, profession.icon) { event ->
      user.profession = profession

      with(event.whoClicked as Player) {
        event.isCancelled = true

        closeInventory()
        sendMessage("The $profession class was selected.")
      }

      val game = GameManager.findByUser(user) ?: return@setItem
      val team = game.findTeam(user) ?: return@setItem
      val teammate = team.findTeammate(user) ?: return@setItem

      if (teammate.profession != profession) {
        teammate.nextProfession = profession

      }
    }
  }
}