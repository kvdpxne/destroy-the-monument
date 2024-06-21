package me.kvdpxne.dtm.commands

import me.kvdpxne.dtm.command.Command
import me.kvdpxne.dtm.command.CommandBuilder
import me.kvdpxne.dtm.data.ArenaDao
import me.kvdpxne.dtm.game.ArenaManager
import me.kvdpxne.dtm.game.ArenaMap
import me.kvdpxne.dtm.shared.WorldLoaderHelper
import me.kvdpxne.dtm.user.UserPerformer

fun createSetArenaMapCommand(): Command = CommandBuilder()
  .name("setArenaMap")
  .parent("dtm")
  .handler<UserPerformer> { performer, parameter ->
    if (2 > parameter.length()) {
      performer.sendMessage("Usage: /dtm SetArenaMap <ARENA_NAME> <WORLD_NAME>")
      return@handler
    }

    val arenaName = parameter.asText()
    val arena = ArenaManager.findArenaByName(arenaName)

    if (null == arena) {
      performer.sendMessage("An game named $arenaName does not exist.")
      return@handler
    }

    val name = parameter.asText(1)

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