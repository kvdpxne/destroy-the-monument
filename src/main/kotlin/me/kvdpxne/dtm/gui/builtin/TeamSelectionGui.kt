package me.kvdpxne.dtm.gui.builtin

import me.kvdpxne.dtm.game.DefaultTeamColor
import me.kvdpxne.dtm.game.Game
import me.kvdpxne.dtm.gui.Gui
import me.kvdpxne.dtm.gui.Rows
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
    val name = game.findSmallerTeam()!!.identity

    game.addTeammate(name) {
      user
    }
    with(it.whoClicked as Player) {
      closeInventory()
      sendMessage("You have been added to the ${name.identifiableName} team.")
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