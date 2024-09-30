package me.kvdpxne.dtm.commands

import me.kvdpxne.dtm.command.Command
import me.kvdpxne.dtm.command.CommandBuilder
import me.kvdpxne.dtm.command.CommandException
import me.kvdpxne.dtm.command.Parameters
import me.kvdpxne.dtm.configuration.Configuration
import me.kvdpxne.dtm.game.Arena
import me.kvdpxne.dtm.game.ArenaService
import me.kvdpxne.dtm.game.MonumentPosition
import me.kvdpxne.dtm.game.Team
import me.kvdpxne.dtm.shared.basics.position.BlockPosition
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
        ?: throw CommandException("&cBŁĄD: &7Nie wybrano zaznaczono żadnego bloku monumentu.\n" +
          "&eINFO: &7Użyj &a/dtm wand &7aby móc zaznaczyć blok monumentu.")

      // Unikatowa nazwa obiektu "Arena".
      val arenaName: String = parameters[0] as String

      //
      val arena: Arena = ArenaService.findArenaByName(arenaName)
        ?: throw CommandException(
          Configuration.NO_FOUND_ARENA
            .replace("{ARENA_NAME}", arenaName)
        )

      val monumentPosition: MonumentPosition<Team> = arena.getMonumentPosition(position)
        ?: throw CommandException("&cBłąd&8: &7Zaznaczona pozycja bloku nie jest monumentem na tej arenie.")

      //
      ArenaService.deleteArenaMonumentPosition(arena, monumentPosition)

      performer.sendMessage(
        "&6&lDTM &7> &7Usunięto monument z areny."
      )
    }
    .build()
}