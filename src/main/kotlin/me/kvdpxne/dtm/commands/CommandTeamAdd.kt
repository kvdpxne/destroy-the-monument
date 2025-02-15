package me.kvdpxne.dtm.commands

import me.kvdpxne.dtm.command.Command
import me.kvdpxne.dtm.command.CommandBuilder
import me.kvdpxne.dtm.command.Parameters
import me.kvdpxne.dtm.command.Performer
import me.kvdpxne.dtm.game.Game
import me.kvdpxne.dtm.game.GameService
import me.kvdpxne.dtm.team.Team
import me.kvdpxne.dtm.translation.formatter.Formatter
import me.kvdpxne.dtm.translation.message.EnumTranslationKey

/**
 * @since 0.1.0
 */
fun createTeamAddCommand(): Command<Performer> {
  // Usage: /dtm team add <GAME_NAME> <TEAM_IDENTITY>
  return CommandBuilder.begin<Performer>("add")
    .parameter(
      Parameters.gameNameParameter()
        .required()
        .build()
    )
    .parameter(
      Parameters.teamNameParameter()
        .required()
        .build()
    )
    .handler { performer, parameters ->
      //
      val game: Game<Team> = attemptObtainGame(performer, parameters)

      //
      val team: Team = attemptObtainTeam(performer, parameters, 1)

      //
      GameService.insertGameTeam(game, team)

      performer.prepareMessage(EnumTranslationKey.COMMAND_TEAM_ADD)
        .format(
          Formatter.begin(2)
            .with("TEAM_NAME", team.name)
            .with("GAME_NAME", game.name)
        )
        .useChat()
        .send()
    }
    .build()
}