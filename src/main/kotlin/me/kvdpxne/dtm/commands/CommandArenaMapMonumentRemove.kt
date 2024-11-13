package me.kvdpxne.dtm.commands

import me.kvdpxne.dtm.arena.Arena
import me.kvdpxne.dtm.arena.ArenaService
import me.kvdpxne.dtm.command.Command
import me.kvdpxne.dtm.command.CommandBuilder
import me.kvdpxne.dtm.command.CommandException
import me.kvdpxne.dtm.command.Parameters
import me.kvdpxne.dtm.configuration.GeneralConfiguration
import me.kvdpxne.dtm.position.BlockPosition
import me.kvdpxne.dtm.position.MonumentPosition
import me.kvdpxne.dtm.team.Team
import me.kvdpxne.dtm.translation.formatter.Formatter
import me.kvdpxne.dtm.translation.message.EnumMessageKey
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
      val position: BlockPosition = performer.user.cache.selectedMonumentPosition
        ?: throw CommandException(
          "&cBŁĄD: &7Nie wybrano zaznaczono żadnego bloku monumentu.\n" +
            "&eINFO: &7Użyj &a/dtm wand &7aby móc zaznaczyć blok monumentu."
        )

      // Unikatowa nazwa obiektu "Arena".
      val arenaName: String = parameters[0] as String

      //
      val arena: Arena = ArenaService.findArenaByName(arenaName)
        ?: throw CommandException(
          GeneralConfiguration.NO_FOUND_ARENA
            .replace("{ARENA_NAME}", arenaName)
        )

      val monumentPosition: MonumentPosition<Team> = arena.getMonumentPosition(position)
        ?: throw CommandException("&cBłąd&8: &7Zaznaczona pozycja bloku nie jest monumentem na tej arenie.")

      //
      ArenaService.deleteArenaMonumentPosition(arena, monumentPosition)

      performer.prepareMessage(EnumMessageKey.COMMAND_ARENA_MAP_MONUMENT_REMOVE)
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