package me.kvdpxne.dtm.commands

import me.kvdpxne.dtm.command.Command
import me.kvdpxne.dtm.command.CommandBuilder
import me.kvdpxne.dtm.command.CommandException
import me.kvdpxne.dtm.configuration.Configuration
import me.kvdpxne.dtm.game.Teammate
import me.kvdpxne.dtm.profession.Ability
import me.kvdpxne.dtm.user.LocalUserPerformer

/**
 * @since 0.1.0
 */
fun createAbilityRenewCommand(): Command<LocalUserPerformer> {
  // Usage: /dtm ability renew
  return CommandBuilder.begin<LocalUserPerformer>("renew")
    .handler { performer, _ ->
      val teammate: Teammate = performer.user.teammate
        ?: throw CommandException(Configuration.NO_IN_GAME_MESSAGE)

      val ability: Ability = teammate.currentProfession.ability
        ?: throw CommandException(Configuration.NO_ABILITY_MESSAGE)

      ability.renew(performer.player!!)
      performer.sendMessage(Configuration.RENEW_ABILITY_MESSAGE)
    }
    .build()
}