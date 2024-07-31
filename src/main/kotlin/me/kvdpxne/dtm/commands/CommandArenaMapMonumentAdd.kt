package me.kvdpxne.dtm.commands

import me.kvdpxne.dtm.command.Command
import me.kvdpxne.dtm.command.CommandBuilder
import me.kvdpxne.dtm.command.ParameterBuilder
import me.kvdpxne.dtm.command.ParameterValidators
import me.kvdpxne.dtm.data.DaoTeam
import me.kvdpxne.dtm.game.ArenaService
import me.kvdpxne.dtm.game.MonumentPosition
import me.kvdpxne.dtm.game.TeamService
import me.kvdpxne.dtm.user.UserPerformer

fun createArenaMapMonumentAddCommand(): Command {
  // Usage: /dtm arena map monument add <ARENA_NAME> <TEAM_IDENTITY>
  return CommandBuilder()
    .name("add")
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

      val position = performer.user.cache.selectedMonumentPosition

      if (null == position) {
        performer.sendMessages(
          "&cBŁĄD: &7Nie wybrano zaznaczono żadnego bloku monumentu.",
          "&eINFO: &7Użyj &a/dtm wand &7aby móc zaznaczyć blok monumentu."
        )
        return@handler
      }

      //
      ArenaService.insertArenaMonumentPosition(
        arena,
        MonumentPosition(
          position.x,
          position.y,
          position.z,
          team
        )
      )

      performer.sendMessage("&6&lDTM &7> &7Dodano nowy blok monumentu dla" +
        "drużyny ${team.displayName()} &7na arenie o nazwie &a${arenaName}&7."
      )
    }
    .build()
}