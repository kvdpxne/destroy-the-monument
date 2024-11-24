package me.kvdpxne.dtm.commands

import me.kvdpxne.dtm.arena.Arena
import me.kvdpxne.dtm.arena.ArenaService
import me.kvdpxne.dtm.command.Command
import me.kvdpxne.dtm.command.CommandBuilder
import me.kvdpxne.dtm.command.Performer
import me.kvdpxne.dtm.translation.formatter.Formatter
import me.kvdpxne.dtm.translation.message.EnumMessageKey

/**
 * @since 0.1.0
 */
fun createArenaCommand(): Command<Performer> {
  /* Usages:
   * /dtm arena add <ARENA_NAME> <GAME_NAME>
   * /dtm arena create <ARENA_NAME>
   * /dtm arena list
   * /dtm arena remove <ARENA_NAME>
   * /dtm arena map
   **/
  return CommandBuilder.begin<Performer>("arena")
    .hub()
    .children(
      createArenaAddCommand(),
      createArenaCreateCommand(),
      createArenaListCommand(),
      createArenaRemoveCommand(),
      createArenaMapCommand(),
    )
    .build()
}

/**
 * @param receiver
 * @param parameters
 * @param index
 *
 * @since 0.1.0
 */
internal fun attemptObtainArena(
  receiver: Performer,
  parameters: Array<Any>,
  index: Int = 0
): Arena {
  // The unique name of the arena retrieved from the passed parameters.
  val name: String = parameters[index] as String

  // Obiekt areny znaleziony na podstawie unikatowej nazwy areny.
  return ArenaService.findArenaByNameOrNull(name)
    ?: receiver.throwMessage(EnumMessageKey.ARENA_NO_FOUND) {
      this@throwMessage.format(
        Formatter.begin(1)
          .with("ARENA_NAME", name)
      )
    }
}