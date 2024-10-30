package me.kvdpxne.dtm.command

import me.kvdpxne.dtm.shared.player.localUser
import me.kvdpxne.dtm.user.LocalUserPerformer
import org.bukkit.command.CommandSender
import org.bukkit.command.defaults.BukkitCommand
import org.bukkit.entity.Player

internal class BukkitCommandHandler internal constructor(
  private val command: Command<Performer>
) : BukkitCommand(
  command.name,
  command.description,
  command.usage,
  command.aliases.toList()
) {

  init {
    this.permission = this.command.permission
  }

  /**
   * @param commandSender
   * @param label
   * @param arguments
   *
   * @since 0.1.0
   */
  override fun execute(
    commandSender: CommandSender,
    label: String,
    arguments: Array<String>
  ): Boolean {
    if (!testPermissionSilent(commandSender)) {
      commandSender.sendMessage("You do not have sufficient privileges to execute this command.")
      return true
    }

    try {
      if (this.command.javaClass.isAssignableFrom(LocalUserPerformer::class.java)) {
        if (commandSender !is Player) {
          commandSender.sendMessage("This command can only be used in the game.")
          return true
        }

        this.command.execute(
          commandSender.localUser.performer,
          arrayOf(label, *arguments)
        )
        return true
      }

      if (commandSender !is Player) {
        this.command.execute(
          BukkitConsolePerformer.INSTANCE,
          arrayOf(label, *arguments)
        )
        return true
      }

      this.command.execute(
        commandSender.localUser.performer,
        arrayOf(label, *arguments)
      )
      return true
    } catch (exception: CommandException) {
      commandSender.sendMessage(exception.message)
    }

    return true
  }

  override fun tabComplete(
    commandSender: CommandSender,
    label: String,
    arguments: Array<String>
  ): List<String> {
    //
    if (!commandSender.hasPermission(this.permission)) {
      return super.tabComplete(commandSender, label, arguments)
    }

    val newArguments = arrayOf(label, *arguments)
    val suggestions = mutableListOf<String>()

    val pair = CommandManager.getSubCommand(newArguments)
      ?: return emptyList()

    pair.first.suggestions(suggestions, arguments, pair.second)
    return suggestions
  }

  fun register() {
    BukkitCommandMapHolder.commandMap?.register("dtm", this)
  }
}