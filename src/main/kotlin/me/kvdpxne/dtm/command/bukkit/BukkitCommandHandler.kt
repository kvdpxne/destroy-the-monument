package me.kvdpxne.dtm.command.bukkit

import me.kvdpxne.dtm.command.CommandHandler
import me.kvdpxne.dtm.command.ExecutionPlaceType
import me.kvdpxne.dtm.command.ExecutionPlaceType.EVERYWHERE
import me.kvdpxne.dtm.command.ExecutionPlaceType.IN_CONSOLE
import me.kvdpxne.dtm.command.ExecutionPlaceType.IN_GAME
import me.kvdpxne.dtm.command.ExecutionPlaceType.IN_GAME_WORLD
import me.kvdpxne.dtm.command.Parameters
import me.kvdpxne.dtm.command.Performer
import me.kvdpxne.dtm.user.UserManager
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.ConsoleCommandSender
import org.bukkit.entity.Player

class BukkitCommandHandler(
  // @formatter:off
              name          : String,
              permission    : String,
              description   : String,
              usage         : String,
              aliases       : List<String>,
  private val handler       : CommandHandler<Performer>,
  private val executionPlace: ExecutionPlaceType
  // @formatter:on
) : Command(name, description, usage, aliases) {

  init {
    // For some reason assigning permissions to the command is not done via
    // a command constructor parameter.
    setPermission(permission)
  }

  private fun handle(
    sender: Player,
    arguments: Array<out String>
  ): Boolean {
    if (!testPermissionSilent(sender)) {
      sender.sendMessage("You do not have sufficient privileges to execute this command.")
      return true
    }

    val user = UserManager.findByIdentifier(sender.uniqueId)!!
    handler(user.performer, Parameters(arguments))
    return true
  }

  override fun execute(
    sender: CommandSender,
    label: String,
    arguments: Array<out String>
  ): Boolean {
    //
    //
    when (executionPlace) {
      IN_CONSOLE -> {
        handler(BukkitConsolePerformer(), Parameters(arguments))
        return true
      }

      IN_GAME, IN_GAME_WORLD -> {
        if (sender !is Player) {
          sender.sendMessage("This command can only be used in the game.")
          return true
        }

        return handle(sender, arguments)
      }

      EVERYWHERE -> {
        if (sender is Player) {
          return handle(sender, arguments)
        }

        if (sender is ConsoleCommandSender) {
          handler(BukkitConsolePerformer(), Parameters(arguments))
          return true
        }
        sender.sendMessage("Not supported yet.")
        return false
      }
    }
  }
}