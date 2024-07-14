package me.kvdpxne.dtm.command

import me.kvdpxne.dtm.command.bukkit.BukkitCommandMapAccessor
import me.kvdpxne.dtm.commands.CommandStart
import me.kvdpxne.dtm.commands.CommandStop
import me.kvdpxne.dtm.commands.createArenaAddCommand
import me.kvdpxne.dtm.commands.createAddMonumentCommand
import me.kvdpxne.dtm.commands.createArenaCommand
import me.kvdpxne.dtm.commands.createTeamAddCommand
import me.kvdpxne.dtm.commands.createCoinsCommand
import me.kvdpxne.dtm.commands.createArenaCreateCommand
import me.kvdpxne.dtm.commands.createGameCreateCommand
import me.kvdpxne.dtm.commands.createTeamCreateCommand
import me.kvdpxne.dtm.commands.createGlobalChatCommand
import me.kvdpxne.dtm.commands.createHelpCommand
import me.kvdpxne.dtm.commands.createJoinCommand
import me.kvdpxne.dtm.commands.createKitCommand
import me.kvdpxne.dtm.commands.createLeaveCommand
import me.kvdpxne.dtm.commands.createArenaMapSetCommand
import me.kvdpxne.dtm.commands.createGameCommand
import me.kvdpxne.dtm.commands.createSetSpawnPointCommand
import me.kvdpxne.dtm.commands.createTeleportBackCommand
import me.kvdpxne.dtm.commands.createTeleportCommand
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
      createAddMonumentCommand(),
      CommandStart.createStartCommand(),
      CommandStop.createStopCommand(),
      createTeleportCommand(),
      createTeleportBackCommand(),
      createWandCommand()
    )
  }
}