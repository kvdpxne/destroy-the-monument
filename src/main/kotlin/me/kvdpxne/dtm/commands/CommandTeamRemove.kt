package me.kvdpxne.dtm.commands

import me.kvdpxne.dtm.command.Command
import me.kvdpxne.dtm.command.CommandBuilder
import me.kvdpxne.dtm.command.Parameters
import me.kvdpxne.dtm.command.Performer
import me.kvdpxne.dtm.game.Game
import me.kvdpxne.dtm.game.GameService
import me.kvdpxne.dtm.team.Team
import me.kvdpxne.dtm.translation.formatter.Formatter
import me.kvdpxne.dtm.translation.message.EnumMessageKey

/**
 * @since 0.1.0
 */
fun createTeamRemoveCommand(): Command<Performer> {
  // Usage: /dtm team remove <TEAM_NAME> [GAME_NAME]
  return CommandBuilder.begin<Performer>("remove")
    .aliases("rem", "rv", "delete", "del")
    .parameter(
      Parameters.gameNameParameter()
        .optional()
        .build()
    )
    .handler { performer, parameters ->
      //
      val team: Team = attemptObtainTeam(performer, parameters)

      // Obiekt gry znaleziony na podstawie unikatowej nazwy gry.
      val game: Game<Team> = attemptObtainGame(performer, parameters, 1)

      //
      GameService.deleteGameTeam(game, team)

      performer.prepareMessage(EnumMessageKey.COMMAND_TEAM_REMOVE)
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