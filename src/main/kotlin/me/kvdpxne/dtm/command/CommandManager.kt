package me.kvdpxne.dtm.command

import me.kvdpxne.dtm.commands.createArenaCommand
import me.kvdpxne.dtm.commands.createCoinsCommand
import me.kvdpxne.dtm.commands.createGameCommand
import me.kvdpxne.dtm.commands.createGlobalChatCommand
import me.kvdpxne.dtm.commands.createHelpCommand
import me.kvdpxne.dtm.commands.createJoinCommand
import me.kvdpxne.dtm.commands.createKitCommand
import me.kvdpxne.dtm.commands.createLeaveCommand
import me.kvdpxne.dtm.commands.createTeleportBackCommand
import me.kvdpxne.dtm.commands.createTeleportCommand
import me.kvdpxne.dtm.commands.createVersionCommand
import me.kvdpxne.dtm.commands.createWandCommand

val registeredCommandMap: MutableMap<Command, MutableList<Command>> = mutableMapOf()

object CommandManager {

  fun registerCommand(command: Command) {
    var parent = command.parent
    if (parent == null) {
      parent = command
    }
    val ff = registeredCommandMap.getOrDefault(parent, mutableListOf())
    ff.add(command)
    registeredCommandMap[parent] = ff
  }

  fun registerCommands(vararg commands: Command) {
    commands.forEach(::registerCommand)
  }

  fun registerBuiltItCommands() {
    registerCommands(
      createArenaCommand(),
      createCoinsCommand(),
      createGameCommand(),
      createGlobalChatCommand(),
      createHelpCommand(),
      createJoinCommand(),
      createKitCommand(),
      createLeaveCommand(),
      createTeleportCommand(),
      createTeleportBackCommand(),
      createVersionCommand(),
      createWandCommand()
    )
  }
}