package me.kvdpxne.dtm.commands

import me.kvdpxne.dtm.arena.Arena
import me.kvdpxne.dtm.arena.ArenaService
import me.kvdpxne.dtm.command.Command
import me.kvdpxne.dtm.command.CommandBuilder
import me.kvdpxne.dtm.command.Parameters
import me.kvdpxne.dtm.position.BlockPosition
import me.kvdpxne.dtm.position.monument.MonumentPositionImpl
import me.kvdpxne.dtm.team.Team
import me.kvdpxne.dtm.translation.formatter.Formatter
import me.kvdpxne.dtm.translation.message.EnumMessageKey
import me.kvdpxne.dtm.user.LocalUserPerformer

/**
 * @since 0.1.0
 */
fun createArenaMapMonumentAddCommand(): Command<LocalUserPerformer> {
  // Usage: /dtm arena map monument add <ARENA_NAME> <TEAM_IDENTITY>
  return CommandBuilder.begin<LocalUserPerformer>("add")
    .parameter(
      Parameters.arenaNameParameter()
        .required()
        .build()
    )
    .parameter(
      Parameters.teamNameParameter()
        .required()
        .build()
    )
    .handler { performer, parameters ->
      //
      val position: BlockPosition = attemptObtainSelectedBlockPosition(performer)

      //
      val arena: Arena = attemptObtainArena(performer, parameters)

      //
      val team: Team = attemptObtainTeam(performer, parameters, 1)

      //
      ArenaService.insertArenaMonumentPosition(
        arena,
        MonumentPositionImpl(
          position.x,
          position.y,
          position.z,
          team
        )
      )

      performer.prepareMessage(EnumMessageKey.COMMAND_ARENA_MAP_MONUMENT_ADD)
        .format(
          Formatter.begin(2)
            .with("TEAM_NAME", team.displayName)
            .with("ARENA_NAME", arena.name)
        )
        .useChat()
        .send()
    }
    .build()
}