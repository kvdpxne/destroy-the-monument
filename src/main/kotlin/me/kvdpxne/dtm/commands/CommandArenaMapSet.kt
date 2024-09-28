package me.kvdpxne.dtm.commands

import me.kvdpxne.dtm.command.Command
import me.kvdpxne.dtm.command.CommandBuilder
import me.kvdpxne.dtm.command.CommandException
import me.kvdpxne.dtm.command.Parameters
import me.kvdpxne.dtm.command.Performer
import me.kvdpxne.dtm.game.Arena
import me.kvdpxne.dtm.game.ArenaService
import me.kvdpxne.dtm.game.ArenaImpl
import me.kvdpxne.dtm.game.ArenaMapImpl
import me.kvdpxne.dtm.shared.WorldLoaderHelper
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
        ?: throw CommandException("&cBłąd: &7Arena o nazwie: &c$arenaName &7nie istnieje.")

      //
      val worldName: String = parameters[1] as String

      //
      val world: World = WorldLoaderHelper.getWorld(worldName)
        ?: throw CommandException("&cBŁĄD: &7Mapa o nazwie &c$worldName &7nie istnieje.")

      ArenaService.updateArenaMap(arena, ArenaMapImpl(world.name, world.uid))

      performer.sendMessage("&6&lDTM &7> &7Przypisano mapę o nazwię &a$worldName &7do areny o nazwie: &a$arenaName&7.")
    }
    .build()
}