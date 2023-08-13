package me.kvdpxne.dtm.command.restricted

import me.kvdpxne.dtm.command.Executor
import me.kvdpxne.dtm.command.Parameter
import me.kvdpxne.dtm.game.ArenaManager
import me.kvdpxne.dtm.game.DefaultTeamColor
import me.kvdpxne.dtm.game.GameManager
import me.kvdpxne.dtm.game.Team
import me.kvdpxne.dtm.user.UserPerformer

object AddArenaCommand : Executor<UserPerformer> {

  // Usage: /dtm AddArena <ARENA_NAME> <GAME_NAME>
  override fun execute(performer: UserPerformer, parameter: Parameter) {
    if (2 > parameter.length()) {
      performer.sendMessage("Usage: /dtm AddArena <ARENA_NAME> <GAME_NAME>")
      return
    }

    val arenaName = parameter.asText()
    val arena = ArenaManager.findArenaByName(arenaName)

    if (null == arena) {
      performer.sendMessage("An arena named $arenaName does not exist.")
      return
    }

    val gameName = parameter.asText(1)
    val game = GameManager.findByName(gameName)

    if (null == game) {
      performer.sendMessage("An game named $gameName does not exist.")
      return
    }

    game.addArena(arena)
    performer.sendMessage("Success")
  }
}