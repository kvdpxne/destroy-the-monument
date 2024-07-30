package me.kvdpxne.dtm.commands

import me.kvdpxne.dtm.command.Command
import me.kvdpxne.dtm.command.CommandBuilder
import me.kvdpxne.dtm.user.UserPerformer

fun createAbilityRenewCommand(): Command {
  // Usage: /dtm ability renew
  return CommandBuilder()
    .name("renew")
    .handler<UserPerformer> { performer, _ ->
      val teammate = performer.user.teammate
      if (null == teammate) {
        performer.sendMessage("&cBłąd: &7Nie jesteś grze.")
        return@handler
      }

      val ability = teammate.currentProfession.ability
      if (null == ability) {
        performer.sendMessage("&cBłąd: &7Twoja profesja nie posiada umiejętności.")
        return@handler
      }

      ability.renew(performer.player!!)
      performer.sendMessage("&6&lDTM &7> &aUmiejętność została odnowiona")
    }
    .build()
}