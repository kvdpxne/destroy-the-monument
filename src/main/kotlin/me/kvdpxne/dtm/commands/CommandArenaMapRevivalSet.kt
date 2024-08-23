package me.kvdpxne.dtm.commands

import me.kvdpxne.dtm.command.Command
import me.kvdpxne.dtm.command.CommandBuilder
import me.kvdpxne.dtm.command.ParameterBuilder
import me.kvdpxne.dtm.command.ParameterValidators
import me.kvdpxne.dtm.data.DaoTeam
import me.kvdpxne.dtm.game.ArenaService
import me.kvdpxne.dtm.game.BaseRevivalPosition
import me.kvdpxne.dtm.user.UserPerformer

fun createArenaMapRevivalSetCommand(): Command {
  // Usage: /dtm arena map revival set <ARENA_NAME> <TEAM_NAME>
  return CommandBuilder()
    .name("set")
    .parameter(
      ParameterBuilder<String>()
        .name("ARENA_NAME")
        .validationBy(ParameterValidators.STRING_VALIDATOR)
        .required()
        .build()
    )
    .parameter(
      ParameterBuilder<String>()
        .name("TEAM_NAME")
        .validationBy(ParameterValidators.STRING_VALIDATOR)
        .required()
        .build()
    )
    .handler<UserPerformer> { performer, parameter ->

      val arenaName = parameter.asText()
      val arena = ArenaService.findArenaByName(arenaName)

      if (null == arena) {
        performer.sendMessage("&cBłąd: &7Arena o nazwie: &c$arenaName &7nie istnieje.")
        return@handler
      }

      val teamName = parameter.asText(1)
      val team = DaoTeam.findTeamByName(teamName)

      if (null == team) {
        performer.sendMessage("&cBłąd: &7Drużyna o nazwie: &c$teamName &7nie istnieje.")
        return@handler
      }

      val location = performer.player?.location ?: return@handler

      //
      //
      ArenaService.insertArenaRevivalPosition(
        arena,
        //
        BaseRevivalPosition(
          location.x,
          location.y,
          location.z,
          location.pitch,
          location.yaw,
          team
        )
      )

      performer.sendMessage("")
    }
    .build()
}