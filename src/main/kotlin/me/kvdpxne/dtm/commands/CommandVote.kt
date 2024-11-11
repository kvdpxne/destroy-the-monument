package me.kvdpxne.dtm.commands

import me.kvdpxne.dtm.arena.voting.ArenaVotingRegistry
import me.kvdpxne.dtm.command.Command
import me.kvdpxne.dtm.command.CommandBuilder
import me.kvdpxne.dtm.command.CommandException
import me.kvdpxne.dtm.command.ParameterBuilder
import me.kvdpxne.dtm.command.ParameterValidators
import me.kvdpxne.dtm.game.LocalGame
import me.kvdpxne.dtm.translation.message.EnumMessageKey
import me.kvdpxne.dtm.user.LocalUserPerformer

fun createVoteCommand(): Command<LocalUserPerformer> {
  return CommandBuilder.begin<LocalUserPerformer>("vote")
    .parameter(
      ParameterBuilder.begin<Int>("NUMBER")
        .validatorHandler(ParameterValidators.INTEGER_VALIDATOR)
        .required()
        .build()
    )
    .handler { performer, parameters ->
      //
      val localGame: LocalGame = performer.user.game
        ?: throw CommandException("&cBłąd&8: Nie jesteś w grze.")

      val votingRegistry: ArenaVotingRegistry = localGame.votingRegistry
        ?: throw CommandException("Głosowanie nie jest obecnie dostępne.")

      val value: Int = parameters[0] as Int
      votingRegistry.castVote(value, performer.user)

      performer.prepareMessage(EnumMessageKey.GAME_VOTING_CAST)
        .withoutFormat()
        .useChat()
        .send()
    }
    .build()
}