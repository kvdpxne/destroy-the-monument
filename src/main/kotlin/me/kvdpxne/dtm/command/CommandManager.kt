package me.kvdpxne.dtm.command

import me.kvdpxne.dtm.command.bukkit.BukkitCommandMapAccessor
import me.kvdpxne.dtm.commands.createHelpCommand
import me.kvdpxne.dtm.commands.createJoinCommand
import me.kvdpxne.dtm.commands.createKitCommand
import me.kvdpxne.dtm.commands.createLeaveCommand
import me.kvdpxne.dtm.commands.StartCommand
import me.kvdpxne.dtm.commands.StopCommand
import me.kvdpxne.dtm.commands.createAddArenaCommand
import me.kvdpxne.dtm.commands.createAddMonumentCommand
import me.kvdpxne.dtm.commands.createAddTeamCommand
import me.kvdpxne.dtm.commands.createCreateArenaCommand
import me.kvdpxne.dtm.commands.createCreateGameCommand
import me.kvdpxne.dtm.commands.createCreateTeamCommand
import me.kvdpxne.dtm.commands.createBaseCommand
import me.kvdpxne.dtm.commands.createGlobalChatCommand
import me.kvdpxne.dtm.commands.createSetArenaMapCommand
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

  fun registerCommands(commands: Array<out Command>) {
    commands.forEach(::registerCommand)
  }

  fun registerBuiltItCommands() {
    // root
    val parent = createBaseCommand()

    val it = mutableListOf(
      createAddArenaCommand(),
      createAddMonumentCommand(),
      createAddTeamCommand(),
      createCreateArenaCommand(),
      createCreateGameCommand(),
      createCreateTeamCommand(),
      createHelpCommand(),
      createJoinCommand(),
      createKitCommand(),
      createLeaveCommand(),
      createSetArenaMapCommand(),
      createSetSpawnPointCommand(),
      StartCommand.createStartCommand(),
      StopCommand.createStopCommand(),
      createTeleportBackCommand(),
      createTeleportCommand(),
      createWandCommand()
    ).onEach {
      it.parent = parent
    }

    it.add(parent)
    it.add(createGlobalChatCommand())

    registerCommands(it.toTypedArray())
    BukkitCommandMapAccessor.registerCommands(it.toTypedArray())
  }
}