package me.kvdpxne.dtm.commands

import me.kvdpxne.dtm.command.Command
import me.kvdpxne.dtm.command.CommandBuilder
import me.kvdpxne.dtm.containers.createGamesContainer
import me.kvdpxne.dtm.containers.createTeamsContainer
import me.kvdpxne.dtm.game.LocalGame
import me.kvdpxne.dtm.user.performer.LocalUserPerformer

/**
 * @since 0.1.0
 */
fun createJoinCommand(): Command<LocalUserPerformer> {
  return CommandBuilder.begin<LocalUserPerformer>("join")
    .handler { performer, _ ->
      //
      val game: LocalGame? = performer.user.game

      if (null != game) {
        createTeamsContainer(performer.user, game).open(performer)
        return@handler
      }

      createGamesContainer(performer.user).open(performer)
    }
    .build()
}