package me.kvdpxne.dtm.commands

import me.kvdpxne.dtm.arena.Arena
import me.kvdpxne.dtm.command.Command
import me.kvdpxne.dtm.command.CommandBuilder
import me.kvdpxne.dtm.command.Parameters
import me.kvdpxne.dtm.command.Performer
import me.kvdpxne.dtm.position.revival.RevivalPosition
import me.kvdpxne.dtm.translation.chains.MessageFormatterChains
import me.kvdpxne.dtm.translation.formatter.Formatter
import me.kvdpxne.dtm.translation.message.EnumMessageKey

/**
 * @since 0.1.0
 */
fun createArenaMapRevivalListCommand(): Command<Performer> {
  // Usage: /dtm arena map revival list <ARENA_NAME>
  return CommandBuilder.begin<Performer>("list")
    .parameter(
      Parameters.arenaNameParameter()
        .required()
        .build()
    )
    .handler { performer, parameters ->
      val arena: Arena = attemptObtainArena(performer, parameters)

      val chains: MessageFormatterChains = performer.prepareMessage(EnumMessageKey.ARENA_MAP_REVIVAL_LIST)
      val formatter: Formatter = Formatter.begin(6)

      for (revivalPosition: RevivalPosition<*> in arena.revivalPositions) {
        chains.copy()
          .format(
            formatter
              .with("TEAM_NAME", revivalPosition.team.name)
              .with("X", revivalPosition.x)
              .with("Y", revivalPosition.y)
              .with("Z", revivalPosition.z)
              .with("PITCH", revivalPosition.pitch)
              .with("YAW", revivalPosition.yaw)
          )
          .useChat()
          .send()
      }
    }
    .build()
}