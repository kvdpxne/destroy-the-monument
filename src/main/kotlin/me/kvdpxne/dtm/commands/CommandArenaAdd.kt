package me.kvdpxne.dtm.commands

import me.kvdpxne.dtm.command.Command
import me.kvdpxne.dtm.command.CommandBuilder
import me.kvdpxne.dtm.command.CommandException
import me.kvdpxne.dtm.command.Parameters
import me.kvdpxne.dtm.command.Performer
import me.kvdpxne.dtm.game.Arena
import me.kvdpxne.dtm.game.ArenaService
import me.kvdpxne.dtm.game.Game
import me.kvdpxne.dtm.game.GameManager
import me.kvdpxne.dtm.game.GameService
import me.kvdpxne.dtm.game.Team

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
      // Unikatowa nazwa obiektu areny przechowywanej w bazie danych.
      val arenaName: String = parameters[0] as String

      // Obiekt areny znaleziony na podstawie unikatowej nazwy areny.
      val arena: Arena = ArenaService.findArenaByName(arenaName)
        ?: throw CommandException("An arena named $arenaName does not exist.")

      // Unikatowa nazwa obiektu gry przechowywanej w bazie danych.
      val gameName: String = parameters[1] as String

      // Obiekt gry znaleziony na podstawie unikatowej nazwy gry.
      val game: Game<Team> = GameService.findGameByName(gameName)
        ?: throw CommandException("An game named $gameName does not exist.")

      //
      GameService.updateGameArena(game, arena)

      //
      GameManager.addArenaToGame(game, arena)

      performer.sendMessage("Success")
    }
    .build()
}