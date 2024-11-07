package me.kvdpxne.dtm.commands

import me.kvdpxne.dtm.command.Command
import me.kvdpxne.dtm.command.CommandBuilder
import me.kvdpxne.dtm.command.CommandException
import me.kvdpxne.dtm.configuration.GeneralConfiguration
import me.kvdpxne.dtm.profession.Ability
import me.kvdpxne.dtm.team.Teammate
import me.kvdpxne.dtm.translation.TranslationService
import me.kvdpxne.dtm.translation.message.MessageKeys
import me.kvdpxne.dtm.user.LocalUserPerformer

/**
 * @since 0.1.0
 */
fun createAbilityDepleteCommand(): Command<LocalUserPerformer> {
  // Usage: /dtm ability deplete
  return CommandBuilder.begin<LocalUserPerformer>("deplete")
    .handler { performer, _ ->

      val teammate: Teammate = performer.user.teammate
        ?: throw CommandException(GeneralConfiguration.NO_IN_GAME_MESSAGE)

      // Umiejętność specjalna aktualnie wybranej klasy.
      val ability: Ability = teammate.currentProfession.ability
        ?: throw CommandException(GeneralConfiguration.NO_ABILITY_MESSAGE)

      //
      ability.renewDelayed(performer.player!!)

      TranslationService.chains()
        .receiver(performer)
        .message(MessageKeys.COMMAND_ABILITY_DEPLETE)
        .send()
    }
    .build()
}