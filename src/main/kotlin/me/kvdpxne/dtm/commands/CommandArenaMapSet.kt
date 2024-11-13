package me.kvdpxne.dtm.commands

import me.kvdpxne.dtm.command.Command
import me.kvdpxne.dtm.command.CommandBuilder
import me.kvdpxne.dtm.command.CommandException
import me.kvdpxne.dtm.command.Parameters
import me.kvdpxne.dtm.command.Performer
import me.kvdpxne.dtm.configuration.GeneralConfiguration
import me.kvdpxne.dtm.arena.Arena
import me.kvdpxne.dtm.arena.ArenaService
import me.kvdpxne.dtm.arena.ArenaMapImpl
import me.kvdpxne.dtm.shared.world.WorldLoaderHelper
import me.kvdpxne.dtm.translation.formatter.Formatter
import me.kvdpxne.dtm.translation.message.EnumMessageKey
import org.bukkit.World

fun createArenaMapSetCommand(): Command<Performer> {
  // Usage: /dtm arena map set <ARENA_NAME> <MAP_NAME>
  return CommandBuilder.begin<Performer>("set")
    .parameter(
      Parameters.arenaNameParameter()
        .required()
        .build()
    )
    .parameter(
      Parameters.arenaWorldNameParameter()
        .required()
        .build()
    )
    .handler { performer, parameters ->
      //
      val arenaName: String = parameters[0] as String

      //
      val arena: Arena = ArenaService.findArenaByName(arenaName)
        ?: throw CommandException(
          GeneralConfiguration.NO_FOUND_ARENA
            .replace("{ARENA_NAME}", arenaName)
        )

      //
      val worldName: String = parameters[1] as String

      //
      val world: World = WorldLoaderHelper.getWorld(worldName)
        ?: throw CommandException("&cBŁĄD: &7Mapa o nazwie &c$worldName &7nie istnieje.")

      ArenaService.updateArenaMap(arena, ArenaMapImpl(world.name, world.uid))

      performer.prepareMessage(EnumMessageKey.COMMAND_ARENA_MAP_SET)
        .format(
          Formatter.begin(2)
            .with("WORLD_NAME", world.name)
            .with("ARENA_NAME", arena.name)
        )
        .useChat()
        .send()
    }
    .build()
}