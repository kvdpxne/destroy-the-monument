package me.kvdpxne.dtm.command

import me.kvdpxne.dtm.gui.builtin.KitGui
import me.kvdpxne.dtm.kit.archer
import me.kvdpxne.dtm.kit.scout
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class KitCommand : Command("kit") {

  override fun execute(sender: CommandSender, label: String, args: Array<out String>): Boolean {
    val player = (sender as Player)
    if (args.isEmpty()) {
      KitGui().open(player)
      return true
    }
    val name = args[0]
    if ("archer".equals(name, true)) {
      archer().items.forEach { item -> player.inventory.setItem(item.index, item.item) }
      return true
    }
    if ("scout".equals(name, true)) {
      scout().items.forEach { item -> player.inventory.setItem(item.index, item.item) }
      return true
    }
    return true
  }
}