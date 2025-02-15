package me.kvdpxne.dtm.commands

import me.kvdpxne.dtm.command.Command
import me.kvdpxne.dtm.command.CommandBuilder
import me.kvdpxne.dtm.command.Performer
import me.kvdpxne.dtm.shared.world.WorldLoaderHelper
import me.kvdpxne.dtm.translation.formatter.Formatter
import me.kvdpxne.dtm.translation.message.EnumTranslationKey
import org.bukkit.World

/**
 * @since 0.1.0
 */
fun createArenaMapCommand(): Command<Performer> {
  /* Usages:
   * /dtm arena map list
   * /dtm arena map monument
   * /dtm arena map revival
   * /dtm arena map set <ARENA_NAME> <MAP_NAME>
   */
  return CommandBuilder.begin<Performer>("map")
    .hub()
    .children(
      createArenaMapListCommand(),
      createArenaMapMonumentCommand(),
      createArenaMapRevivalCommand(),
      createArenaMapSetCommand()
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
fun attemptObtainWorld(
  receiver: Performer,
  parameters: Array<Any>,
  index: Int = 0
): World {
  //
  val name: String = parameters[index] as String

  //
  val world: World = WorldLoaderHelper.getWorld(name)
    ?: receiver.throwMessage(EnumTranslationKey.NONEXISTENT_WORLD) {
      this@throwMessage.format(
        Formatter.begin(1)
          .with("WORLD_NAME", name)
      )
    }

  return world
}