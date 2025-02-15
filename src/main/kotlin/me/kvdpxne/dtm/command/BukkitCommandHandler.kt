package me.kvdpxne.dtm.command

import me.kvdpxne.dtm.configuration.GeneralConfiguration
import me.kvdpxne.dtm.shared.player.localUser
import me.kvdpxne.dtm.translation.message.MessageHolderException
import me.kvdpxne.dtm.translation.TranslationService
import me.kvdpxne.dtm.translation.chains.message
import me.kvdpxne.dtm.translation.formatter.Formatter
import me.kvdpxne.dtm.translation.message.EnumTranslationKey
import me.kvdpxne.dtm.translation.message.Message
import me.kvdpxne.dtm.translation.message.MultipleMessages
import me.kvdpxne.dtm.translation.message.SingleMessage
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

  fun execute(
    performer: Performer,
    label: String,
    arguments: Array<out String>
  ) {
    try {
      this.command.execute(
        performer,
        arrayOf(label, *arguments),
      )
    } catch (exception: MessageHolderException) {
      //
      val message: Message<*> = exception.context

      if (message is SingleMessage) {
        performer.sendMessage(message.content)
        return
      }

      if (message is MultipleMessages) {
        for (contentLine: String in message.content) {
          performer.sendMessage(contentLine)
        }
        return
      }

      error("Unknown message: $message")
    } catch (exception: CommandException) {
      performer.sendMessage(exception.message.toString())
      exception.printStackTrace()
    }
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
    if (GeneralConfiguration.USE_FA_F) {
      if (!testPermissionSilent(commandSender)) {
        TranslationService.chains()
          .receiver(commandSender.asPerformer())
          .message(EnumTranslationKey.COMMAND_INSUFFICIENT_PRIVILEGES)
          .format(
            Formatter.begin(1)
              .with("PRIVILEGE_NAME", this.permission)
          )
          .useChat()
          .send()

        return true
      }
    } else {
      if (!testPermission(commandSender)) {
        return true
      }
    }

    if (this.command.javaClass.isAssignableFrom(LocalUserPerformer::class.java)) {
      if (commandSender !is Player) {
        TranslationService.chains()
          .receiver(commandSender.asPerformer())
          .message(EnumTranslationKey.COMMAND_IN_GAME)
          .withoutFormat()
          .useChat()
          .send()
        return true
      }

      this.execute(commandSender.localUser.performer, label, arguments)
      return true
    }

    if (commandSender !is Player) {
      this.execute(BukkitConsolePerformer.INSTANCE, label, arguments)
      return true
    }

    this.execute(commandSender.localUser.performer, label, arguments)
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