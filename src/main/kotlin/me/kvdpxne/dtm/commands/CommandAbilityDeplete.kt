package me.kvdpxne.dtm.commands

import me.kvdpxne.dtm.command.Command
import me.kvdpxne.dtm.command.CommandBuilder
import me.kvdpxne.dtm.profession.Ability
import me.kvdpxne.dtm.translation.message.EnumMessageKey
import me.kvdpxne.dtm.user.LocalUserPerformer

/**
 * Usage: `/dtm ability deplete`
 *
 * @since 0.1.0
 */
fun createAbilityDepleteCommand(): Command<LocalUserPerformer> {
  return CommandBuilder.begin<LocalUserPerformer>("deplete")
    .handler { performer: LocalUserPerformer, _: Array<Any> ->
      //
      val ability: Ability = attemptObtainAbility(performer)

      //
      ability.renewDelayed(performer.player)

      performer.prepareMessage(EnumMessageKey.COMMAND_ABILITY_DEPLETE)
        .withoutFormat()
        .useChat()
        .send()
    }
    .build()
}