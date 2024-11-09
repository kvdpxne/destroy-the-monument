package me.kvdpxne.dtm.commands

import me.kvdpxne.dtm.command.Command
import me.kvdpxne.dtm.command.CommandBuilder
import me.kvdpxne.dtm.command.Parameters
import me.kvdpxne.dtm.command.Performer
import me.kvdpxne.dtm.arena.Arena
import me.kvdpxne.dtm.arena.ArenaService
import me.kvdpxne.dtm.arena.ArenaImpl
import me.kvdpxne.dtm.translation.TranslationService
import me.kvdpxne.dtm.translation.formatter.Formatter
import me.kvdpxne.dtm.translation.message.EnumMessageKey

/**
 * @since 0.1.0
 */
fun createArenaCreateCommand(): Command<Performer> {
  // Usage: /dtm arena create <ARENA_NAME>
  return CommandBuilder.begin<Performer>("create")
    .parameter(
      Parameters.arenaNameParameter()
        .required()
        .build()
    )
    .handler { performer, parameters ->
      // Unikatowa nazwa obiektu areny.
      val arenaName: String = parameters[0] as String

      // Nowo utworzony obiekt areny.
      val arena: Arena = ArenaImpl(arenaName)

      //
      ArenaService.insertArena(arena)

      TranslationService.chains()
        .receiver(performer)
        .message(EnumMessageKey.COMMAND_ARENA_CREATE)
        .format(
          Formatter.begin(1)
            .with("ARENA_NAME", arena.name)
        )
        .useChat()
        .send()
    }
    .build()
}