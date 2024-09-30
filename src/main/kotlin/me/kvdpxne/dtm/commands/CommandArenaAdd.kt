package me.kvdpxne.dtm.commands

import me.kvdpxne.dtm.command.Command
import me.kvdpxne.dtm.command.CommandBuilder
import me.kvdpxne.dtm.command.CommandException
import me.kvdpxne.dtm.command.Parameters
import me.kvdpxne.dtm.command.Performer
import me.kvdpxne.dtm.configuration.Configuration
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
        ?: throw CommandException(
          Configuration.NO_FOUND_ARENA
            .replace("{ARENA_NAME}", arenaName)
        )

      // Unikatowa nazwa obiektu gry przechowywanej w bazie danych.
      val gameName: String = parameters[1] as String

      // Obiekt gry znaleziony na podstawie unikatowej nazwy gry.
      val game: Game<Team> = GameService.findGameByName(gameName)
        ?: throw CommandException(
          Configuration.NO_FOUND_GAME
            .replace("{GAME_NAME}", gameName)
        )

      // Aktualizuje dane w bazie danych.
      GameService.updateGameArena(game, arena)

      //
      GameManager.addArenaToGame(game, arena)

      performer.sendMessage("&6&lDTM &7> &7Arena o nazwie &a${arena.name} &7została przypisana do gry o nazwie &a${game.name}&7.")
    }
    .build()
}