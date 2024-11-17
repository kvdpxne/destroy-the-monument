package me.kvdpxne.dtm.commands

import me.kvdpxne.dtm.command.Command
import me.kvdpxne.dtm.command.CommandBuilder
import me.kvdpxne.dtm.command.Performer
import me.kvdpxne.dtm.position.BlockPosition
import me.kvdpxne.dtm.translation.message.EnumMessageKey
import me.kvdpxne.dtm.user.LocalUserPerformer

/**
 * @since 0.1.0
 */
fun createArenaMapMonumentCommand(): Command<Performer> {
  /* Usages:
   * /dtm arena map monument add <ARENA_NAME> <TEAM_NAME>
   * /dtm arena map monument list <ARENA_NAME>
   * /dtm arena map monument remove <ARENA_NAME> <TEAM_NAME>
   */
  return CommandBuilder.begin<Performer>("monument")
    .hub()
    .children(
      createArenaMapMonumentAddCommand(),
      createArenaMapMonumentListCommand(),
      createArenaMapMonumentRemoveCommand()
    )
    .build()
}

/**
 * @param receiver
 *
 * @since 0.1.0
 */
fun attemptObtainSelectedBlockPosition(
  receiver: LocalUserPerformer
): BlockPosition {
  return receiver.user.cache.selectedMonumentPosition
    ?: receiver.throwMessage(EnumMessageKey.ARENA_MAP_BLOCK_NO_SELECT) {
      this@throwMessage.withoutFormat()
    }
}