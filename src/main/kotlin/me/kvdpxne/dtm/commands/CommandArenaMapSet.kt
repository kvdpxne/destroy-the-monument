package me.kvdpxne.dtm.commands

import me.kvdpxne.dtm.command.Command
import me.kvdpxne.dtm.command.CommandBuilder
import me.kvdpxne.dtm.command.ParameterBuilder
import me.kvdpxne.dtm.command.Performer
import me.kvdpxne.dtm.command.builderArenaNameParameter
import me.kvdpxne.dtm.data.ArenaDao
import me.kvdpxne.dtm.game.ArenaManager
import me.kvdpxne.dtm.game.ArenaMap
import me.kvdpxne.dtm.shared.WorldLoaderHelper
import me.kvdpxne.dtm.user.UserPerformer

fun createArenaMapSetCommand(): Command {
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
      val arena = arguments.asFoundArena()

      if (null == arena) {
        performer.sendMessage("An game named ${arguments.asText()} does not exist.")
        return@handler
      }

      val name = arguments.asText(1)

      WorldLoaderHelper.getWorld(name).let {
        if (null == it) {
          performer.sendMessage("World named \"$name\" does not exist.")
          return@handler
        }

        arena.map = ArenaMap(it.uid, it.name)
        ArenaDao.update(arena)
        performer.sendMessage("Success!")
      }
    }
    .build()
}