package me.kvdpxne.dtm.scoreboard

import java.util.UUID
import java.util.concurrent.ConcurrentHashMap
import me.kvdpxne.dtm.game.LocalGame
import me.kvdpxne.dtm.game.scoreboards.GameScoreboardState
import me.kvdpxne.dtm.shared.GameUuid
import me.kvdpxne.dtm.team.LocalTeam
import org.bukkit.entity.Player

object ScoreboardManager {

  /**
   * @since 0.1.0
   */
  private val gameStates = ConcurrentHashMap<GameUuid, GameScoreboardState>()

  /**
   * @since 0.1.0
   */
  private val playerGameMap = ConcurrentHashMap<UUID, GameUuid>()

  @Synchronized
  fun registerGame(game: LocalGame) {
    gameStates[game.identifier] = GameScoreboardState(game)
  }

  @Synchronized
  fun unregisterGame(gameId: GameUuid) {
    gameStates.remove(gameId)?.cleanup()
  }

  /**
   * @since 0.1.0
   */
  fun handleHostageJoin(
    player: Player,
    game: LocalGame
  ) {
    synchronized(playerGameMap) {
      playerGameMap[player.uniqueId] = game.identifier
    }
    gameStates[game.identifier]?.playerJoinLobby(player)
  }

  fun playerJoinGame(player: Player, game: LocalGame, team: LocalTeam) {
    synchronized(playerGameMap) {
      playerGameMap[player.uniqueId] = game.identifier
    }
    gameStates[game.identifier]?.playerJoinGame(player, team)
  }

  fun playerSpectate(player: Player, game: LocalGame) {
    synchronized(playerGameMap) {
      playerGameMap[player.uniqueId] = game.identifier
    }
    gameStates[game.identifier]?.playerSpectate(player)
  }

  fun playerLeave(player: Player) {
    val gameId = synchronized(playerGameMap) {
      playerGameMap.remove(player.uniqueId)
    } ?: return

    gameStates[gameId]?.playerLeave(player)
  }

  fun handlePlayerKill(killer: Player, victim: Player) {
    val (killerGameId, victimGameId) = synchronized(playerGameMap) {
      Pair(playerGameMap[killer.uniqueId], playerGameMap[victim.uniqueId])
    }

    if (killerGameId != null && killerGameId == victimGameId) {
      gameStates[killerGameId]?.handlePlayerKill(killer, victim)
    }
  }

  fun updateGameScoreboards(gameId: GameUuid) {
    gameStates[gameId]?.updateAllScoreboards()
  }
}