package me.kvdpxne.dtm.commands

import me.kvdpxne.dtm.command.Command
import me.kvdpxne.dtm.command.CommandBuilder
import me.kvdpxne.dtm.command.ParameterBuilder
import me.kvdpxne.dtm.command.Performer
import me.kvdpxne.dtm.command.builderArenaNameParameter
import me.kvdpxne.dtm.data.DaoArena
import me.kvdpxne.dtm.game.BaseArenaMap
import me.kvdpxne.dtm.shared.WorldLoaderHelper

fun createArenaMapSetCommand(): Command {
  // Usage: /dtm arena map set <ARENA_NAME> <MAP_NAME>
  return CommandBuilder()
    .name("set")
    .parameter(
      builderArenaNameParameter()
        .required()
        .build()
    )
    .parameter(
      ParameterBuilder<String>()
        .name("map_name")
        .required()
        .build()
    )
    .handler<Performer> { performer, arguments ->
      val arenaName = arguments.asText()
      val arena = me.kvdpxne.dtm.game.ArenaService.findArenaByName(arenaName)

      if (null == arena) {
        performer.sendMessage("&cBłąd: &7Arena o nazwie: &c$arenaName &7nie istnieje.")
        return@handler
      }

      val mapName = arguments.asText(1)
      val map = WorldLoaderHelper.getWorld(mapName)

      if (null == map) {
        performer.sendMessage("&cBŁĄD: &7Mapa o nazwie &c$mapName &7nie istnieje.")
        return@handler
      }

      arena.map = BaseArenaMap(map.name, map.uid.toString())
      DaoArena.updateArena(arena)
      performer.sendMessage("&6&lDTM &7> &7Przypisano mapę o nazwię &a$mapName &7do areny o nazwie: &a$arenaName&7.")
    }
    .build()
}