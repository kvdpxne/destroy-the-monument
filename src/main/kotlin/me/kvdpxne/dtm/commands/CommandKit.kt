package me.kvdpxne.dtm.commands

import me.kvdpxne.dtm.command.Command
import me.kvdpxne.dtm.command.CommandBuilder
import me.kvdpxne.dtm.containers.createProfessionsContainer
import me.kvdpxne.dtm.user.performer.LocalUserPerformer

/**
 * @since 0.1.0
 */
fun createKitCommand(): Command<LocalUserPerformer> {
  return CommandBuilder.begin<LocalUserPerformer>("kit")
    .aliases("kits", "class", "classes", "profession", "professions")
    .handler { performer, _ ->
      createProfessionsContainer(performer.user).open(performer)
    }
    .build()
}