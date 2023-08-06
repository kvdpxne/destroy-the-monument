package me.kvdpxne.dtm.command.restricted

import me.kvdpxne.dtm.command.Executor
import me.kvdpxne.dtm.command.Parameter
import me.kvdpxne.dtm.game.ArenaManager
import me.kvdpxne.dtm.game.DefaultTeamColor
import me.kvdpxne.dtm.shared.SelectedPositionStorage
import me.kvdpxne.dtm.user.UserPerformer

object AddMonumentCommand : Executor<UserPerformer> {

  // Usage: /dtm AddMonument <ARENA_NAME> <TEAM_IDENTITY>
  override fun execute(performer: UserPerformer, parameter: Parameter) {
    if (2 > parameter.length()) {
      performer.sendMessage("the command requires 2 arguments.")
      return
    }

    val arenaName = parameter.asText()
    val arena = ArenaManager.findArenaByName(arenaName)

    if (null == arena) {
      performer.sendMessage("An arena named $arenaName does not exist.")
      return
    }

    val teamName = parameter.asText(1)
    val team = DefaultTeamColor.findByIdentityKey(teamName)

    if (null == team) {
      performer.sendMessage("An team named $teamName does not exist.")
      return
    }

    val player = performer.getPlayer() ?: return
    val position = SelectedPositionStorage.selectedBlocks[player.uniqueId]

    if (null == position) {
      performer.sendMessage("No block is selected.")
      return
    }

    arena.addMonument(team, position)
    player.sendMessage("Success")
  }
}