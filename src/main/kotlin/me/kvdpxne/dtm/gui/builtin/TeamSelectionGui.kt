package me.kvdpxne.dtm.gui.builtin

import me.kvdpxne.dtm.game.DefaultTeamColor
import me.kvdpxne.dtm.game.GameManager
import me.kvdpxne.dtm.gui.Gui
import me.kvdpxne.dtm.gui.Rows
import me.kvdpxne.dtm.user.User
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

class TeamSelectionGui(name: String, user: User) : Gui("Wybór drużyny", Rows.ONE) {

  init {
    val coloredWool = ItemStack(Material.WOOL)

    val game = GameManager.findGameByArenaName(name)

    coloredWool.durability = 14
    setItem(0, coloredWool) {
      game?.addTeammate(DefaultTeamColor.RED, user)
    }

    coloredWool.durability = 11
    setItem(8, coloredWool) {
      game?.addTeammate(DefaultTeamColor.BLUE, user)
    }
  }
}