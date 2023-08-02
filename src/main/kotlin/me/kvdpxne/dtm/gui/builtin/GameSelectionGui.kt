package me.kvdpxne.dtm.gui.builtin

import me.kvdpxne.dtm.game.GameManager
import me.kvdpxne.dtm.gui.Gui
import me.kvdpxne.dtm.gui.Rows
import me.kvdpxne.dtm.user.User
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

fun createGameSelectionGui(user: User) = GameManager.games.let {
  Gui("Wybierz Gre", Rows.findRowBySize(it.size)).apply {
    var next = 0
    it.forEach { (key, game) ->
      setItem(next, ItemStack(Material.CLAY).apply {
        itemMeta = itemMeta.apply {
          displayName = game.name
          lore = listOf(key.toString())
        }
      }) { event ->
        game.addHostage(user)
        with(event.whoClicked as Player) {
          closeInventory()
          sendMessage("You have been added to the ${game.name} game.")
        }
      }
      next++
    }
  }
}