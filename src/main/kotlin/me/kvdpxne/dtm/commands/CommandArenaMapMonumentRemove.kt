package me.kvdpxne.dtm.commands

import me.kvdpxne.dtm.arena.Arena
import me.kvdpxne.dtm.arena.ArenaService
import me.kvdpxne.dtm.command.Command
import me.kvdpxne.dtm.command.CommandBuilder
import me.kvdpxne.dtm.command.Parameters
import me.kvdpxne.dtm.position.BlockPosition
import me.kvdpxne.dtm.position.monument.MonumentPosition
import me.kvdpxne.dtm.team.Team
import me.kvdpxne.dtm.translation.formatter.Formatter
import me.kvdpxne.dtm.translation.message.EnumTranslationKey
import me.kvdpxne.dtm.user.LocalUserPerformer

/**
 * @since 0.1.0
 */
fun createArenaMapMonumentRemoveCommand(): Command<LocalUserPerformer> {
  return CommandBuilder.begin<LocalUserPerformer>("remove")
    .aliases("rm", "delete", "del")
    .parameter(
      Parameters.arenaNameParameter()
        .required()
        .build()
    )
    .handler { performer, parameters ->
      //
      val position: BlockPosition = attemptObtainSelectedBlockPosition(performer)

      //
      val arena: Arena = attemptObtainArena(performer, parameters)

      //
      val monumentPosition: MonumentPosition<Team> = arena.getMonumentPosition(position)
        ?: performer.throwMessage(EnumTranslationKey.ARENA_MAP_MONUMENT_INCORRECT_SELECT) {
          this@throwMessage.withoutFormat()
        }

      //
      ArenaService.deleteArenaMonumentPosition(arena, monumentPosition)

      performer.prepareMessage(EnumTranslationKey.COMMAND_ARENA_MAP_MONUMENT_REMOVE)
        .format(
          Formatter.begin(2)
            .with("TEAM_NAME", monumentPosition.team.displayName)
            .with("ARENA_NAME", arena.name)
        )
        .useChat()
        .send()
    }
    .build()
}