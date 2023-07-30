package me.kvdpxne.dtm.gui.builtin

import me.kvdpxne.dtm.game.GameManager
import me.kvdpxne.dtm.gui.Gui
import me.kvdpxne.dtm.gui.Rows
import me.kvdpxne.dtm.user.User
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

fun createGameSelectionGui(user: User): Gui {
  val games = GameManager.identifierGameMap
  return Gui(
    "Wybierz Gre",
    Rows.findRowBySize(games.size)
  ).apply {
    var next = 0
    games.forEach { (key, value) ->
      setItem(next, ItemStack(Material.CLAY).apply {
        itemMeta = itemMeta.apply {
          displayName = value.name
          lore = listOf(key.toString())
        }
      }) {
        user.run {
          value.hostages[identifier] = this
        }
      }
      next++
    }
  }
}