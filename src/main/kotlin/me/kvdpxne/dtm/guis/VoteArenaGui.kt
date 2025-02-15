package me.kvdpxne.dtm.guis

import me.kvdpxne.dtm.arena.Arena
import me.kvdpxne.dtm.arena.voting.ArenaVoting
import me.kvdpxne.dtm.arena.voting.ArenaVotingRegistry
import me.kvdpxne.dtm.game.LocalGame
import me.kvdpxne.dtm.gui.Gui
import me.kvdpxne.dtm.gui.GuiArrangement
import me.kvdpxne.dtm.gui.Rows
import me.kvdpxne.dtm.shared.item.toBuilder
import me.kvdpxne.dtm.translation.TranslationService
import me.kvdpxne.dtm.translation.formatter.Formatter
import me.kvdpxne.dtm.translation.message.EnumTranslationKey
import me.kvdpxne.dtm.user.LocalUser
import org.bukkit.Material
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack

fun createArenaSelectionGui(
  game: LocalGame,
  user: LocalUser
): Gui {

  val votingRegistry: ArenaVotingRegistry = game.votingRegistry
    ?: error(
      "Voting to select an arena is not available in the game with " +
        "the identifier ${game.identifier}."
    )

  val iterator: IntIterator = when (votingRegistry.size) {
    2 -> GuiArrangement.TWO_ITEMS_ONE_ROW
    3 -> GuiArrangement.THREE_ITEMS_ONE_ROW
    4 -> GuiArrangement.FOUR_ITEMS_ONE_ROW
    else -> error(
      "Size ${votingRegistry.size} is not supported and cannot be " +
        "displayed in the gui."
    )
  }.iterator()

  val gui = Gui(
    TranslationService
      .findLocalMessagesOrDefault(user.locale)
      .findRawMessage(EnumTranslationKey.GUI_ARENA_VOTING.messageKey),
    Rows.ONE
  )

  for (votingArena: ArenaVoting in votingRegistry.arenas) {
    val arena: Arena = votingArena.arena

    gui.setItem(
      iterator.nextInt(),
      ItemStack(Material.STONE).toBuilder()
        .name("&6&l${arena.name}")
        .lore(
          *user.performer
            .prepareMessage(EnumTranslationKey.GUI_ARENA)
            .format(
              Formatter.begin(2)
                .with("MONUMENT_COUNT", arena.monumentCount)
                .with("ARENA_SIZE", "?")
            )
            .raw<Array<String>>()
        )
        .build()
    ) { event: InventoryClickEvent ->

      votingRegistry.castVote(votingArena.identifier, user)
      event.whoClicked.closeInventory()

      user.performer.prepareMessage(EnumTranslationKey.GAME_VOTING_CAST)
        .withoutFormat()
        .useChat()
        .send()
    }
  }

  return gui
}