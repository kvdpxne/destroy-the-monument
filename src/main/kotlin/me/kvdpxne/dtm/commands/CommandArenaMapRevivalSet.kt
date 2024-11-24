package me.kvdpxne.dtm.commands

import me.kvdpxne.dtm.arena.Arena
import me.kvdpxne.dtm.arena.ArenaService
import me.kvdpxne.dtm.command.Command
import me.kvdpxne.dtm.command.CommandBuilder
import me.kvdpxne.dtm.command.Parameters
import me.kvdpxne.dtm.position.revival.RevivalPositionImpl
import me.kvdpxne.dtm.team.Team
import me.kvdpxne.dtm.translation.formatter.Formatter
import me.kvdpxne.dtm.translation.message.EnumMessageKey
import me.kvdpxne.dtm.user.LocalUserPerformer
import org.bukkit.Location

/**
 * @since 0.1.0
 */
fun createArenaMapRevivalSetCommand(): Command<LocalUserPerformer> {
  // Usage: /dtm arena map revival set <ARENA_NAME> <TEAM_NAME>
  return CommandBuilder.begin<LocalUserPerformer>("set")
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
      val arena: Arena = attemptObtainArena(performer, parameters)

      //
      val team: Team = attemptObtainTeam(performer, parameters, 1)

      //
      val location: Location = performer.player!!.location

      ArenaService.insertArenaRevivalPosition(
        arena,
        RevivalPositionImpl(
          location.x,
          location.y,
          location.z,
          location.pitch,
          location.yaw,
          team
        )
      )

      performer.prepareMessage(EnumMessageKey.COMMAND_ARENA_MAP_REVIVAL_SET)
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