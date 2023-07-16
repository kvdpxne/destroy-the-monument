package me.kvdpxne.dtm.command

import me.kvdpxne.dtm.game.DefaultTeamColor
import me.kvdpxne.dtm.game.Teammate
import me.kvdpxne.dtm.gui.builtin.KitGui
import me.kvdpxne.dtm.profession.archer
import me.kvdpxne.dtm.profession.scout
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
      val archer = archer()
      archer.kit.equip(Teammate(player, archer.kit, DefaultTeamColor.BLUE))
      return true
    }
    if ("scout".equals(name, true)) {
      val scout = scout()
      scout.kit.equip(Teammate(player, scout.kit, DefaultTeamColor.RED))
      return true
    }
    return true
  }
}