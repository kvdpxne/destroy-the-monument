package me.kvdpxne.dtm.shared.debug

import java.util.logging.Logger
import me.kvdpxne.dico.Dico
import org.bukkit.entity.Player

/**
 * A utility class for debugging purposes.
 *
 * @since 0.1.0
 */
object Debug {

  /**
   * The logger used for console output.
   *
   * @since 0.1.0
   */
  private lateinit var logger: Logger

  /**
   * Whether to print debug messages to the console.
   *
   * @since 0.1.0
   */
  private var _printInConsole: Boolean = true

  /**
   * Whether to print debug messages in-game to OP players.
   *
   * @since 0.1.0
   */
  private var _printInGame: Boolean = true

  /**
   * Gets whether debug messages are printed to the console.
   *
   * @return True if debug messages are printed to the console.
   * @since 0.1.0
   */
  val printInConsole: Boolean
    get() = _printInConsole

  /**
   * Gets whether debug messages are printed in-game to OP players.
   *
   * @return True if debug messages are printed in-game to OP players.
   * @since 0.1.0
   */
  val printInGame: Boolean
    get() = _printInGame

  /**
   * Initializes the debug logger.
   *
   * @param logger The logger to use for console output.
   * @throws IllegalStateException If the logger is already initialized.
   * @since 0.1.0
   */
  fun initialize(
    logger: Logger
  ) {
    check(!this::logger.isInitialized) {
      "Debug logger already initialized."
    }

    this.logger = logger
  }

  /**
   * Logs a debug message.
   *
   * @param message The message to log.
   * @since 0.1.0
   */
  fun log(
    message: () -> String
  ) {
    if (this._printInConsole) {
      println(message())
    }

    if (this._printInGame) {
      for (player: Player in Dico.getLocalPlayers().asArray()) {
        if (!player.isOp) {
          continue
        }

        player.sendRawMessage("[Debug] ${message()}")
      }
    }
  }

  /**
   * Toggles whether debug messages are printed to the console.
   *
   * @since 0.1.0
   */
  fun togglePrintInConsole() {
    this._printInConsole = !this._printInConsole
  }

  /**
   * Toggles whether debug messages are printed in-game to OP players.
   *
   * @since 0.1.0
   */
  fun togglePrintInGame() {
    this._printInGame = !this._printInGame
  }
}