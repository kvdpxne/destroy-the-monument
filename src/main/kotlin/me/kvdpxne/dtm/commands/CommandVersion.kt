package me.kvdpxne.dtm.commands

import me.kvdpxne.dtm.Constants
import me.kvdpxne.dtm.command.Command
import me.kvdpxne.dtm.command.CommandBuilder
import me.kvdpxne.dtm.command.Performer

/**
 * @since 0.1.0
 */
fun createVersionCommand(): Command<Performer> {
  return CommandBuilder.begin<Performer>("version")
    .aliases("ver", "v")
    .handler { performer, _ ->
      performer.sendMessages(
        arrayOf(
          "",
          "&6&lDTM &8> &7The current version of the plugin:",
          "&7Full name: &f${Constants.FULL_NAME}",
          "&7Version: &f${Constants.VERSION}-${Constants.TARGET_PLATFORM}",
          "&7Is legacy: ${if (Constants.IS_LEGACY) "&ayes" else "&cno"}",
          "&7Is development: ${if (Constants.IS_DEVELOPMENT) "&ayes" else "&cno"}",
          "Github: "
        )
      )

      if (Constants.IS_DEVELOPMENT) {
        performer.sendMessages(
          arrayOf(
            "",
            "&6&lDTM &8> &cThe server uses the development version of the plugin,",
            "&6&lDTM &8> &cremember that this version may be unstable and contain",
            "&6&lDTM &8> &cbugs, even critical ones."
          )
        )
      }
    }
    .build()
}