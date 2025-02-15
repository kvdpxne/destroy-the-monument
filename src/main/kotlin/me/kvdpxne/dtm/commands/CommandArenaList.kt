package me.kvdpxne.dtm.commands

import me.kvdpxne.dtm.arena.Arena
import me.kvdpxne.dtm.arena.ArenaService
import me.kvdpxne.dtm.command.Command
import me.kvdpxne.dtm.command.CommandBuilder
import me.kvdpxne.dtm.command.Performer
import me.kvdpxne.dtm.translation.chains.MessageFormatterChains
import me.kvdpxne.dtm.translation.formatter.Formatter
import me.kvdpxne.dtm.translation.message.EnumTranslationKey

/**
 * @since 0.1.0
 */
fun createArenaListCommand(): Command<Performer> {
  // Usage: /dtm arena list
  return CommandBuilder.begin<Performer>("list")
    .handler { performer: Performer, _: Array<Any> ->
      val arenas: Iterable<Arena> = ArenaService.findArenas()

      val chains: MessageFormatterChains = performer.prepareMessage(EnumTranslationKey.COMMAND_ARENA_LIST_FORMAT)
      val formatter: Formatter = Formatter.begin(1)

      performer.prepareMessage(EnumTranslationKey.COMMAND_ARENA_LIST_TITLE)
        .withoutFormat()
        .useChat()
        .send()

      for (arena: Arena in arenas) {
        chains.copy()
          .format(formatter.with("ARENA_NAME", arena.name))
          .useChat()
          .send()
      }
    }
    .build()
}