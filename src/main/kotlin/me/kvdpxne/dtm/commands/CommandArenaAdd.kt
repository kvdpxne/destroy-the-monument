package me.kvdpxne.dtm.commands

import me.kvdpxne.dtm.arena.Arena
import me.kvdpxne.dtm.command.Command
import me.kvdpxne.dtm.command.CommandBuilder
import me.kvdpxne.dtm.command.Parameters
import me.kvdpxne.dtm.command.Performer
import me.kvdpxne.dtm.game.Game
import me.kvdpxne.dtm.game.GameManager
import me.kvdpxne.dtm.game.GameService
import me.kvdpxne.dtm.team.Team
import me.kvdpxne.dtm.translation.formatter.Formatter
import me.kvdpxne.dtm.translation.message.EnumMessageKey

/**
 * @since 0.1.0
 */
fun createArenaAddCommand(): Command<Performer> {
  // Usage: /dtm arena add <ARENA_NAME> <GAME_NAME>
  return CommandBuilder.begin<Performer>("add")
    .parameter(
      Parameters.arenaNameParameter()
        .required()
        .build()
    )
    .parameter(
      Parameters.localGameNameParameter()
        .required()
        .build()
    )
    .handler { performer, parameters ->
      //
      val arena: Arena = attemptObtainArena(performer, parameters)

      //
      val game: Game<Team> = attemptObtainGame(performer, parameters, 1)

      // Aktualizuje dane w bazie danych.
      GameService.updateGameArena(game, arena)

      //
      GameManager.addArenaToGame(game, arena)

      performer.prepareMessage(EnumMessageKey.COMMAND_ARENA_ADD)
        .format(
          Formatter.begin(2)
            .with("ARENA_NAME", arena.name)
            .with("GAME_NAME", game.name)
        )
        .useChat()
        .send()
    }
    .build()
}