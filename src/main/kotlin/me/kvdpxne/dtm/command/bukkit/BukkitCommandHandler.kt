package me.kvdpxne.dtm.command.bukkit

import me.kvdpxne.dtm.command.BaseCommand
import me.kvdpxne.dtm.command.ExecutionPlaceType
import me.kvdpxne.dtm.command.Parameter
import me.kvdpxne.dtm.user.UserManager
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class BukkitCommandHandler(
  name       : String,
  permission : String       = "",
  description: String       = "",
  usage      : String       = "",
  aliases    : List<String> = emptyList(),

  private val executionPlace: ExecutionPlaceType = ExecutionPlaceType.EVERYWHERE
) : Command(name, description, usage, aliases) {

  init {
    // For some reason assigning permissions to the command is not done via
    // a command constructor parameter.
    setPermission(permission)
  }

  override fun execute(sender: CommandSender, label: String, arguments: Array<out String>): Boolean {
    if (sender !is Player) {
      if (executionPlace == ExecutionPlaceType.IN_GAME) {
        sender.sendMessage("")
      }
      return true
    }

    if (!testPermissionSilent(sender)) {
      sender.sendMessage("You do not have sufficient privileges to execute this command.")
      return true
    }

    val user = UserManager.findByIdentifier(sender.uniqueId)!!
    BaseCommand.execute(user.performer, Parameter(arguments))
    return true
  }
}