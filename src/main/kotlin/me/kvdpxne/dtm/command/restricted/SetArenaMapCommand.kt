package me.kvdpxne.dtm.command.restricted

import me.kvdpxne.dtm.command.Executor
import me.kvdpxne.dtm.command.Parameter
import me.kvdpxne.dtm.data.ArenaDao
import me.kvdpxne.dtm.game.ArenaManager
import me.kvdpxne.dtm.game.ArenaMap
import me.kvdpxne.dtm.shared.WorldLoaderHelper
import me.kvdpxne.dtm.user.UserPerformer

object SetArenaMapCommand : Executor<UserPerformer> {

  // Usage: /dtm SetArenaMap <ARENA_NAME> <WORLD_NAME>
  override fun execute(performer: UserPerformer, parameter: Parameter) {
    if (2 > parameter.length()) {
      performer.sendMessage("Usage: /dtm SetArenaMap <ARENA_NAME> <WORLD_NAME>")
      return
    }

    val arenaName = parameter.asText()
    val arena = ArenaManager.findArenaByName(arenaName)

    if (null == arena) {
      performer.sendMessage("An game named $arenaName does not exist.")
      return
    }

    val name = parameter.asText(1)

    WorldLoaderHelper.getWorld(name).let {
      if (null == it) {
        performer.sendMessage("World named \"$name\" does not exist.")
        return
      }

      arena.map = ArenaMap(it.uid, it.name)
      ArenaDao.update(arena)
      performer.sendMessage("Success!")
    }
  }
}