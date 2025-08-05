package me.kvdpxne.dtm.commands

import me.kvdpxne.dtm.command.Command
import me.kvdpxne.dtm.command.CommandBuilder
import me.kvdpxne.dtm.command.Performer
import me.kvdpxne.dtm.profession.Ability
import me.kvdpxne.dtm.team.Teammate
import me.kvdpxne.dtm.translation.message.EnumTranslationKey
import me.kvdpxne.dtm.user.performer.LocalUserPerformer

/**
 * @since 0.1.0
 */
fun createAbilityCommand(): Command<Performer> {
  /* Usages:
   * /dtm ability deplete
   * /dtm ability renew
   */
  return CommandBuilder.begin<Performer>("ability")
    .aliases("skill", "capability")
    .hub()
    .children(
      createAbilityDepleteCommand(),
      createAbilityRenewCommand()
    )
    .build()
}

/**
 * @param receiver
 *
 * @since 0.1.0
 */
internal fun attemptObtainAbility(
  receiver: LocalUserPerformer
): Ability {
  // A teammate retrieved from the team to which the user is registered.
  val teammate: Teammate = receiver.teammate
    ?: receiver.throwMessage(EnumTranslationKey.MUST_IN_GAME) {
      this@throwMessage.withoutFormat()
    }

  // The special ability of the currently used/selected profession.
  val ability: Ability = teammate.currentProfession.ability
    ?: receiver.throwMessage(EnumTranslationKey.MUST_HAVE_ABILITY) {
      this@throwMessage.withoutFormat()
    }

  return ability
}