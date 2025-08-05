package me.kvdpxne.dtm.game.scoreboards

import fr.mrmicky.fastboard.FastBoard
import java.util.UUID
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicInteger
import me.kvdpxne.dico.Dico
import me.kvdpxne.dtm.game.LocalGame
import me.kvdpxne.dtm.shared.TeamUuid
import me.kvdpxne.dtm.team.LocalTeam
import me.kvdpxne.dtm.user.LocalUser
import me.kvdpxne.dtm.user.LocalUserManager
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.entity.Player

class GameScoreboardState(
  private val game: LocalGame
) {
  enum class ScoreboardType { LOBBY, IN_GAME, SPECTATOR }

  private val minPlayers = 4
  private val maxPlayers = 16

  private var gameTimeSeconds: AtomicInteger? = null
  private var lobbyCountdown: AtomicInteger? = null
  private var gameTimeTaskId: Int? = null
  private var lobbyCountdownTaskId: Int? = null

  private val teams: Map<TeamUuid, LocalTeam> = game.teams.associateBy { it.identifier }
  private val playerStates = ConcurrentHashMap<UUID, ScoreboardType>()
  private val playerBoards = ConcurrentHashMap<UUID, FastBoard>()

  // Stały tytuł dla wszystkich scoreboardów
  private val scoreboardTitle = ChatColor.translateAlternateColorCodes('&', "&7[&6&lDTM&7]")

  init {
    when {
      game.isInitialized || game.isStarting -> checkLobbyState()
      game.isRunning -> startGameTimer()
    }
  }

  @Synchronized
  fun cleanup() {
    stopLobbyCountdown()
    stopGameTimer()
    playerBoards.values.forEach(FastBoard::delete)
  }

  private fun startLobbyCountdown() {
    if (lobbyCountdown != null) return

    lobbyCountdown = AtomicInteger(60)
    lobbyCountdownTaskId = Bukkit.getScheduler().runTaskTimer(
      Bukkit.getPluginManager().plugins.first(),
      Runnable {
        lobbyCountdown?.decrementAndGet()
        if (lobbyCountdown?.get() == 0) {
          startGame()
        }
        updateAllScoreboards()
      },
      20, 20
    ).taskId
  }

  private fun stopLobbyCountdown() {
    lobbyCountdownTaskId?.let {
      Bukkit.getScheduler().cancelTask(it)
      lobbyCountdownTaskId = null
    }
    lobbyCountdown = null
  }

  private fun startGameTimer() {
    if (gameTimeSeconds != null) return

    gameTimeSeconds = AtomicInteger(0)
    gameTimeTaskId = Bukkit.getScheduler().runTaskTimer(
      Bukkit.getPluginManager().plugins.first(),
      Runnable {
        gameTimeSeconds?.incrementAndGet()
        updateAllScoreboards()
      },
      20, 20
    ).taskId
  }

  private fun stopGameTimer() {
    gameTimeTaskId?.let {
      Bukkit.getScheduler().cancelTask(it)
      gameTimeTaskId = null
    }
    gameTimeSeconds = null
  }

  @Synchronized
  private fun checkLobbyState() {
    when {
      playersInLobby >= minPlayers && lobbyCountdown == null -> startLobbyCountdown()
      playersInLobby < minPlayers && lobbyCountdown != null -> stopLobbyCountdown()
    }
  }

  private val playersInLobby: Int
    @Synchronized get() = playerStates.values.count { it == ScoreboardType.LOBBY }

  private val playersInGame: Int
    @Synchronized get() = playerStates.values.count { it == ScoreboardType.IN_GAME }

  // Formatowanie czasu w formacie MM:SS
  private fun formatTime(seconds: Int): String {
    val minutes = seconds / 60
    val secs = seconds % 60
    return String.format("%02d:%02d", minutes, secs)
  }

  // Pobierz użytkownika jako LocalUser
  private fun getLocalUser(player: Player): LocalUser? {
    return LocalUserManager.findUserByIdentifierOrNull(player.uniqueId) as? LocalUser
  }

  // Pobierz statystyki z bieżącej gry dla gracza
  private fun getCurrentGameStats(player: Player): Pair<Int, Int> {
    val user = getLocalUser(player) ?: return Pair(0, 0)
    val teammate = game.findTeammateByHostage(user) ?: return Pair(0, 0)
    return Pair(teammate.statistics.kills, teammate.statistics.deaths)
  }

  // Pobierz monety gracza
  private fun getPlayerCoins(player: Player): Long {
    return getLocalUser(player)?.wallet?.coins ?: 0
  }

  // Aktualizuj scoreboard dla gracza w lobby
  private fun updateLobbyScoreboard(board: FastBoard, player: Player) {
    val timeText = lobbyCountdown?.get()?.let {
      "&e&l${formatTime(it)}"
    } ?: "&eoczekiwanie"

    val lines = listOf(
      "&7Start gry: $timeText",
      "",
      "&7Max: &6&l$maxPlayers",
      "&7Min: &6&l$minPlayers",
      "",
      "&7Zapisani: &6&l$playersInLobby",
      "&7Online: &6&l${Dico.getLocalPlayers().asCollection().size}",
      "",
      if (lobbyCountdown == null) {
        "&eOczekiwanie na minimalną liczbę graczy"
      } else {
        "&eStart za: ${lobbyCountdown!!.get()}s"
      },
      "",
      "",
      "&7Monety:",
      "&6&l${getPlayerCoins(player)}"
    )

    board.updateLines(lines.map { ChatColor.translateAlternateColorCodes('&', it) })
  }

  // Aktualizuj scoreboard dla gracza w grze
  private fun updateInGameScoreboard(board: FastBoard, player: Player) {
    val (kills, deaths) = getCurrentGameStats(player)
    val team1 = teams.values.firstOrNull()
    val team2 = teams.values.elementAtOrNull(1)

    val lines = listOf(
      "&7Czas gry: &e&l${formatTime(gameTimeSeconds?.get() ?: 0)}",
      "",
      "&c&lCzerwoni:",
      "&7- Gracze: &6&l${team1?.size ?: 0}",
      "&7- Monumenty: &6&l${team1?.health ?: 0}",
      "",
      "&b&lNiebiescy:",
      "&7- Gracze: &6&l${team2?.size ?: 0}",
      "&7- Monumenty: &6&l${team2?.health ?: 0}",
      "",
      "&7Zabojstwa: &6&l$kills",
      "&7Smierci: &6&l$deaths",
      "",
      "&7Monety:",
      "&6&l${getPlayerCoins(player)}"
    )

    board.updateLines(lines.map { ChatColor.translateAlternateColorCodes('&', it) })
  }

  // Aktualizuj scoreboard dla obserwatora
  private fun updateSpectatorScoreboard(board: FastBoard, player: Player) {
    val team1 = teams.values.firstOrNull()
    val team2 = teams.values.elementAtOrNull(1)

    val lines = listOf(
      "&7Czas gry: &e&l${formatTime(gameTimeSeconds?.get() ?: 0)}",
      "",
      "&c&lCzerwoni:",
      "&7- Gracze: &6&l${team1?.size ?: 0}",
      "&7- Monumenty: &6&l${team1?.health ?: 0}",
      "",
      "&b&lNiebiescy:",
      "&7- Gracze: &6&l${team2?.size ?: 0}",
      "&7- Monumenty: &6&l${team2?.health ?: 0}",
      "",
      "&eJesteś obserwatorem",
      "",
      "&7Monety:",
      "&6&l${getPlayerCoins(player)}"
    )

    board.updateLines(lines.map { ChatColor.translateAlternateColorCodes('&', it) })
  }

  @Synchronized
  fun playerJoinLobby(player: Player) {
    playerStates[player.uniqueId] = ScoreboardType.LOBBY
    setScoreboard(player, ScoreboardType.LOBBY)
    checkLobbyState()
    updateAllScoreboards()
  }

  @Synchronized
  fun playerJoinGame(player: Player, team: LocalTeam) {
    playerStates[player.uniqueId] = ScoreboardType.IN_GAME
    setScoreboard(player, ScoreboardType.IN_GAME)
    checkLobbyState()
    updateAllScoreboards()
  }

  @Synchronized
  fun playerSpectate(player: Player) {
    playerStates[player.uniqueId] = ScoreboardType.SPECTATOR
    setScoreboard(player, ScoreboardType.SPECTATOR)
    updateAllScoreboards()
  }

  @Synchronized
  fun playerLeave(player: Player) {
    playerStates.remove(player.uniqueId)
    playerBoards.remove(player.uniqueId)?.delete()
    checkLobbyState()
    updateAllScoreboards()
  }

  @Synchronized
  fun handlePlayerKill(killer: Player, victim: Player) {
    val killerUser = getLocalUser(killer) ?: return
    val victimUser = getLocalUser(victim) ?: return

    // Aktualizuj statystyki bieżącej gry
    val killerTeammate = game.findTeammateByHostage(killerUser)
    val victimTeammate = game.findTeammateByHostage(victimUser)

    killerTeammate?.statistics?.addKills(1)
    victimTeammate?.statistics?.addDeaths(1)

    // Aktualizuj statystyki ogólne
    killerUser.statistics.addKills(1)
    killerUser.statistics.markAsModified()

    victimUser.statistics.addDeaths(1)
    victimUser.statistics.markAsModified()

    // Dodaj monety
    killerUser.wallet.addCoins(5)

    updateScoreboard(killer)
    updateScoreboard(victim)
  }

  @Synchronized
  fun startGame() {
    stopLobbyCountdown()
    startGameTimer()
  }

  @Synchronized
  fun endGame() {
    stopGameTimer()
  }

  private fun setScoreboard(player: Player, type: ScoreboardType) {
    playerBoards.computeIfAbsent(player.uniqueId) {
      FastBoard(player).apply {
        updateTitle(scoreboardTitle)
      }
    }
    updateScoreboard(player)
  }

  private fun updateScoreboard(player: Player) {
    val board = playerBoards[player.uniqueId] ?: return
    val type = playerStates[player.uniqueId] ?: return

    when (type) {
      ScoreboardType.LOBBY -> updateLobbyScoreboard(board, player)
      ScoreboardType.IN_GAME -> updateInGameScoreboard(board, player)
      ScoreboardType.SPECTATOR -> updateSpectatorScoreboard(board, player)
    }
  }

  @Synchronized
  fun updateAllScoreboards() {
    playerBoards.forEach { (playerId, board) ->
      val type = playerStates[playerId] ?: return@forEach
      val player = Bukkit.getPlayer(playerId) ?: return@forEach

      when (type) {
        ScoreboardType.LOBBY -> updateLobbyScoreboard(board, player)
        ScoreboardType.IN_GAME -> updateInGameScoreboard(board, player)
        ScoreboardType.SPECTATOR -> updateSpectatorScoreboard(board, player)
      }
    }
  }
}