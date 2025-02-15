package me.kvdpxne.dtm.commands

import me.kvdpxne.dtm.command.Command
import me.kvdpxne.dtm.command.CommandBuilder
import me.kvdpxne.dtm.profession.Ability
import me.kvdpxne.dtm.translation.message.EnumTranslationKey
import me.kvdpxne.dtm.user.LocalUserPerformer

/**
 * Usage: `/dtm ability renew`
 *
 * @since 0.1.0
 */
fun createAbilityRenewCommand(): Command<LocalUserPerformer> {
  return CommandBuilder.begin<LocalUserPerformer>("renew")
    .handler { performer: LocalUserPerformer, _: Array<Any> ->
      //
      val ability: Ability = attemptObtainAbility(performer)

      //
      ability.renew(performer.player)

      performer.prepareMessage(EnumTranslationKey.COMMAND_ABILITY_RENEW)
        .withoutFormat()
        .useChat()
        .send()
    }
    .build()
}