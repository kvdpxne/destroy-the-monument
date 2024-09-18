package me.kvdpxne.dtm.commands

import me.kvdpxne.dtm.command.Command
import me.kvdpxne.dtm.command.CommandBuilder
import me.kvdpxne.dtm.command.CommandException
import me.kvdpxne.dtm.command.Parameters
import me.kvdpxne.dtm.command.Performer
import me.kvdpxne.dtm.data.DaoGameArena
import me.kvdpxne.dtm.game.Arena
import me.kvdpxne.dtm.game.ArenaService
import me.kvdpxne.dtm.game.Game
import me.kvdpxne.dtm.game.GameService
import me.kvdpxne.dtm.game.Team

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
      // Nazwa obiektu areny przechowywanej w bazie danych.
      val arenaName: String = parameters[0] as String

      //
      val arena: Arena = ArenaService.findArenaByName(arenaName)
        ?: throw CommandException("An arena named $arenaName does not exist.")

      // Nazwa obiektu gry przechowywanej w bazie danych.
      val gameName: String = parameters[1] as String

      //
      val game: Game<Team> = GameService.findGameByName(gameName)
        ?: throw CommandException("An game named $gameName does not exist.")

      DaoGameArena.insertGameArena(game.identifier, arena.identifier)
      performer.sendMessage("Success")
    }
    .build()
}