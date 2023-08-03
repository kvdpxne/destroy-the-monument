package me.kvdpxne.dtm.gui.builtin

import me.kvdpxne.dtm.game.DefaultTeamColor
import me.kvdpxne.dtm.game.Game
import me.kvdpxne.dtm.gui.Gui
import me.kvdpxne.dtm.gui.Rows
import me.kvdpxne.dtm.user.User
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

fun createTeamSelectionGui(game: Game, user: User) = Gui("Wybór drużyny", Rows.ONE).apply {
  val coloredWool = ItemStack(Material.WOOL)

  coloredWool.durability = 14
  setItem(0, coloredWool) {
    game.addTeammate(DefaultTeamColor.RED) { user }
  }

  setItem(4, ItemStack(Material.OBSIDIAN)) {
    val name = game.teams.minBy {
      it.teammates.size
    }.identity

    game.addTeammate(name) { user }
  }

  coloredWool.durability = 11
  setItem(8, coloredWool) {
    game.addTeammate(DefaultTeamColor.BLUE) { user }
  }
}