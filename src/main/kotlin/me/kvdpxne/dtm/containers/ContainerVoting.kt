package me.kvdpxne.dtm.containers

import me.kvdpxne.dtm.arena.Arena
import me.kvdpxne.dtm.arena.voting.ArenaVoting
import me.kvdpxne.dtm.arena.voting.ArenaVotingRegistry
import me.kvdpxne.dtm.container.Container
import me.kvdpxne.dtm.container.ContainerBuilder
import me.kvdpxne.dtm.container.ContainerTypes
import me.kvdpxne.dtm.container.Rows
import me.kvdpxne.dtm.container.displayName
import me.kvdpxne.dtm.game.LocalGame
import me.kvdpxne.dtm.shared.item.ItemBuilder
import me.kvdpxne.dtm.shared.item.lore
import me.kvdpxne.dtm.translation.formatter.Formatter
import me.kvdpxne.dtm.translation.message.EnumTranslationKey
import me.kvdpxne.dtm.user.LocalUser
import me.kvdpxne.dtm.user.performer.LocalUserPerformer

fun createVotingContainer(
  user: LocalUser,
  game: LocalGame
): Container<LocalUserPerformer> {
  //
  val votingRegistry: ArenaVotingRegistry = game.votingRegistry
    ?: error(
      "Voting to select an arena is not available in the game with " +
        "the identifier ${game.identifier}."
    )

  val iterator: ByteIterator = when (votingRegistry.size) {
    2 -> byteArrayOf(2, 6)
    3 -> byteArrayOf(2, 4, 6)
    4 -> byteArrayOf(1, 3, 5, 7)
    else -> error(
      "Size ${votingRegistry.size} is not supported and cannot be " +
        "displayed in the gui."
    )
  }.iterator()

  val containerBuilder: ContainerBuilder<LocalUserPerformer> =
    ContainerBuilder.begin<LocalUserPerformer>()
      .owner(user.identifier)
      .type(ContainerTypes.CHEST)
      .size(Rows.ONE)
      .displayName(user.locale, EnumTranslationKey.GUI_SELECT_GAME)

  for (votingArena: ArenaVoting in votingRegistry.arenas) {
    val arena: Arena = votingArena.arena
    val next: Byte = iterator.next()

    containerBuilder.slot(
      next,
      ItemBuilder.begin("STONE")
        .name("&6&l${arena.name}")
        .lore(user.locale, EnumTranslationKey.GUI_ARENA) {
          Formatter.begin(2)
            .with("MONUMENT_COUNT", arena.monumentCount)
            .with("ARENA_SIZE", "?")
        }
        .build()
    ) { _: LocalUserPerformer ->
      votingRegistry.castVote(votingArena.identifier, user)
      user.performer.close()

      user.prepareMessage(EnumTranslationKey.GAME_VOTING_CAST)
        .withoutFormat()
        .useChat()
        .send()
    }
  }

  return containerBuilder.build()
}