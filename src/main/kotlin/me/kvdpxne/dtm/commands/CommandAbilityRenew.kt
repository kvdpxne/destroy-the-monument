package me.kvdpxne.dtm.commands

import me.kvdpxne.dtm.command.Command
import me.kvdpxne.dtm.command.CommandBuilder
import me.kvdpxne.dtm.shared.bukkit.fillExperienceBar
import me.kvdpxne.dtm.user.UserPerformer

fun createAbilityRenewCommand(): Command {
  return CommandBuilder()
    .name("renew")
    .handler<UserPerformer> { performer, _ ->
      val user = performer.user
      val game = user.game ?: return@handler
      val teammate = game.findTeam(user)?.findTeammate(user) ?: return@handler

      teammate.currentProfession.ability?.let {
        it.cancelCooldown()
        it.markReady()
        performer.player?.fillExperienceBar()
        performer.sendMessage("Umiejętność została odnowiona")
      }
    }
    .build()
}