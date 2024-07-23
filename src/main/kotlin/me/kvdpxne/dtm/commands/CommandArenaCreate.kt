package me.kvdpxne.dtm.commands

import me.kvdpxne.dtm.command.Command
import me.kvdpxne.dtm.command.CommandBuilder
import me.kvdpxne.dtm.command.ParameterBuilder
import me.kvdpxne.dtm.game.ArenaManager
import me.kvdpxne.dtm.user.UserPerformer

// Usage: /dtm arena create <ARENA_NAME>
fun createArenaCreateCommand(): Command {
  return CommandBuilder()
    .name("create")
    .parameter(
      ParameterBuilder<String>()
        .name("arena_name")
        .required()
        .build()
    )
    .handler<UserPerformer> { performer, arguments ->
      ArenaManager.createArena(arguments.asText())
      performer.sendMessage("Success")
    }
    .build()
}