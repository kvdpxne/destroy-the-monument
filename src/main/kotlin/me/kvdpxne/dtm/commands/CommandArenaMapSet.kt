package me.kvdpxne.dtm.commands

import me.kvdpxne.dtm.arena.Arena
import me.kvdpxne.dtm.arena.map.ArenaMapImpl
import me.kvdpxne.dtm.arena.ArenaService
import me.kvdpxne.dtm.command.Command
import me.kvdpxne.dtm.command.CommandBuilder
import me.kvdpxne.dtm.command.Parameters
import me.kvdpxne.dtm.command.Performer
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
      val arena: Arena = attemptObtainArena(performer, parameters)

      //
      val world: World = attemptObtainWorld(performer, parameters, 1)

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