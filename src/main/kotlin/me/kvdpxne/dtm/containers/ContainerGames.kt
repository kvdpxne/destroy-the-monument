package me.kvdpxne.dtm.containers

import me.kvdpxne.dtm.container.Container
import me.kvdpxne.dtm.container.ContainerBuilder
import me.kvdpxne.dtm.container.ContainerTypes
import me.kvdpxne.dtm.container.Rows
import me.kvdpxne.dtm.container.displayName
import me.kvdpxne.dtm.game.Game
import me.kvdpxne.dtm.game.GameManager
import me.kvdpxne.dtm.game.GameStates
import me.kvdpxne.dtm.game.LocalGame
import me.kvdpxne.dtm.placeholder.Placeholders
import me.kvdpxne.dtm.shared.item.ItemBuilder
import me.kvdpxne.dtm.shared.item.Items
import me.kvdpxne.dtm.shared.item.displayName
import me.kvdpxne.dtm.shared.item.lore
import me.kvdpxne.dtm.shared.player.equipItemsOfTeamSelection
import me.kvdpxne.dtm.shared.player.reset
import me.kvdpxne.dtm.translation.formatter.Formatter
import me.kvdpxne.dtm.translation.message.EnumTranslationKey
import me.kvdpxne.dtm.user.LocalUser
import me.kvdpxne.dtm.user.performer.LocalUserPerformer

/**
 * Creates a menu interface showing available games for the user to join.
 *
 * Each game is displayed with its current status (waiting, in progress, etc.)
 * and details like arena name. When a user selects a game, they are added to
 * it, their player state is reset, and team selection opens if the game has
 * started. If no games are available, a placeholder message is shown.
 *
 * @param user The player for whom the game selection menu is created.
 * @since 0.1.0
 */
fun createGamesContainer(
  user: LocalUser
): Container<LocalUserPerformer> {
  //
  val games: Collection<Game<*>> = GameManager.games

  //
  val containerBuilder: ContainerBuilder<LocalUserPerformer> =
    ContainerBuilder.begin<LocalUserPerformer>()
      .owner(user.identifier)
      .type(ContainerTypes.CHEST)
      .size(Rows.nearestRows(games.size))
      .displayName(user.locale, EnumTranslationKey.CONTAINER_GAMES_TITLE)

  //
  val itemBuilder: ItemBuilder = Items.stainedClayItem

  //
  if (games.isEmpty()) {
    return containerBuilder
      .slot(
        13,
        itemBuilder.copy()
          .generation(14)
          .raw()
      )
      .build()
  }

  var next: Byte = 0
  for (game: Game<*> in games) {
    if (game !is LocalGame) {
      continue
    }

    containerBuilder.slot(
      next,
      itemBuilder.copy()
        .generation(
          when (game.state) {
            GameStates.INITIALIZED, GameStates.STARTING -> 5
            GameStates.RUNNING -> 13
            GameStates.ENDING, GameStates.STOPPING -> 3
            else -> 9
          }
        )
        .displayName(user.locale, EnumTranslationKey.CONTAINER_GAMES_ITEM_NAME) {
          Formatter.begin(1)
            .with(Placeholders.INDEX, next)
            .with(Placeholders.MEMBER_COUNT, game.numberOfHostages)
            .with(Placeholders.MEMBER_LIMIT, Integer.MAX_VALUE)
        }
        .lore(user.locale, EnumTranslationKey.CONTAINER_GAMES_ITEM_LORE) {
          Formatter.begin(2)
            .with(Placeholders.CURRENT_ARENA, game.currentArena?.name ?: "unknown")
            .with(Placeholders.GAME_UID, game.identifier)
        }
        .raw()
    ) { _: LocalUserPerformer ->
      if (game.addHostage(user)) {
        // TODO wiadomosc informujaca ze uzytkownik jest juz w lobby do ktorego chce dolaczyc
      }

      user.prepareMessage(EnumTranslationKey.GAME_LOBBY_JOIN)
        .format(
          Formatter.begin(1)
            .with(Placeholders.INDEX, next)
        )
        .useChat()
        .send()

      user.performer.player?.reset()
      user.performer.player?.equipItemsOfTeamSelection()
      user.performer.close()

      //
      if (game.isRunning) {
        createTeamsContainer(user, game).open(user.performer)
      }
    }

    next++
  }

  return containerBuilder.build()
}