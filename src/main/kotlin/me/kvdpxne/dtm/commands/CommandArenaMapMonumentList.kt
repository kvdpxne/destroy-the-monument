package me.kvdpxne.dtm.commands

import me.kvdpxne.dtm.arena.Arena
import me.kvdpxne.dtm.command.Command
import me.kvdpxne.dtm.command.CommandBuilder
import me.kvdpxne.dtm.command.Parameters
import me.kvdpxne.dtm.command.Performer
import me.kvdpxne.dtm.translation.chains.MessageFormatterChains
import me.kvdpxne.dtm.translation.formatter.Formatter
import me.kvdpxne.dtm.translation.message.EnumTranslationKey

/**
 * @since 0.1.0
 */
fun createArenaMapMonumentListCommand(): Command<Performer> {
  // Usage: /dtm arena map monument list <ARENA_NAME>
  return CommandBuilder.begin<Performer>("list")
    .parameter(
      Parameters.arenaNameParameter()
        .required()
        .build()
    )
    .handler { performer, parameters ->
      val arena: Arena = attemptObtainArena(performer, parameters)

      val chains: MessageFormatterChains = performer.prepareMessage(EnumTranslationKey.ARENA_MAP_MONUMENT_LIST)
      val formatter: Formatter = Formatter.begin(4)

      arena.monumentPositions
        .sortedBy {
          it.team.name
        }
        .forEach {
          chains.copy()
            .format(
              formatter
                .with("TEAM_NAME", it.team.name)
                .with("X", it.x)
                .with("Y", it.y)
                .with("Z", it.z)
            )
            .useChat()
            .send()
        }
    }
    .build()
}