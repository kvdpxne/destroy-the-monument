package me.kvdpxne.dtm.commands

import me.kvdpxne.dtm.command.Command
import me.kvdpxne.dtm.command.CommandBuilder
import me.kvdpxne.dtm.game.LocalGame
import me.kvdpxne.dtm.shared.player.equipItemsOfGameSelection
import me.kvdpxne.dtm.shared.player.moveToLobby
import me.kvdpxne.dtm.shared.player.reset
import me.kvdpxne.dtm.translation.message.EnumMessageKey
import me.kvdpxne.dtm.user.LocalUserPerformer

/**
 * @since 0.1.0
 */
fun createLeaveCommand(): Command<LocalUserPerformer> {
  return CommandBuilder.begin<LocalUserPerformer>("leave")
    .aliases("quit", "exit")
    .handler { performer, _ ->
      // Obiekt lokalnej gry, do której jest przypisany użytkownik.
      val localGame: LocalGame = performer.user.game
        ?: performer.throwMessage(EnumMessageKey.MUST_IN_GAME) {
          this@throwMessage.withoutFormat()
        }

      //
      if (localGame.isInArena(performer.user)) {
        localGame.findTeammateByHostage(performer.user)?.leave()
        performer.sendMessage("You left the game.")
        return@handler
      }

      localGame.removeHostage(performer.user)

      //
      performer.player?.reset()
      performer.player?.moveToLobby()
      performer.player?.equipItemsOfGameSelection()

      performer.sendMessage("You left the game.")
    }
    .build()
}
