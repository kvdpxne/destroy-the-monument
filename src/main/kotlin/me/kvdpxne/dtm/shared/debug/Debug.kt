package me.kvdpxne.dtm.shared.debug

import java.util.logging.Logger
import me.kvdpxne.dico.Dico
import me.kvdpxne.dtm.configuration.DebugConfiguration
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
  private var logger: Logger? = null

  /**
   * Whether to print debug messages to the console.
   *
   * @since 0.1.0
   */
  @Suppress("SuspiciousVarProperty")
  private var _printInConsole: Boolean = DebugConfiguration.consolePrintOutput
    get() = DebugConfiguration.consolePrintOutput

  /**
   * Whether to print debug messages in-game to OP players.
   *
   * @since 0.1.0
   */
  @Suppress("SuspiciousVarProperty")
  private var _printInGame: Boolean = DebugConfiguration.gamePrintOutput
    get() = DebugConfiguration.gamePrintOutput

  /**
   * Gets whether debug messages are printed to the console.
   *
   * @return True if debug messages are printed to the console.
   * @since 0.1.0
   */
  val printInConsole: Boolean
    get() = this._printInConsole

  /**
   * Gets whether debug messages are printed in-game to OP players.
   *
   * @return True if debug messages are printed in-game to OP players.
   * @since 0.1.0
   */
  val printInGame: Boolean
    get() = this._printInGame

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
    check(null == this.logger) {
      "Debug logger already initialized."
    }

    this.logger = logger
  }

  /**
   * @throws IllegalStateException
   *
   * @since 0.1.0
   */
  fun destroy() {
    check(null != this.logger) {
      "Debug logger destroyed."
    }

    this.logger = null
  }

  /**
   * @param message
   *
   * @since 0.1.0
   */
  private fun constructMessage(
    message: String,
  ): String {
    return "[DEBUG] $message"
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
      val constructedMessage: String = this.constructMessage(message())
      this.logger?.info(constructedMessage)
    }

    if (this._printInGame) {
      for (player: Player in Dico.getLocalPlayers().asCollection()) {
        if (!player.isOp) {
          continue
        }

        val constructedMessage: String = this.constructMessage(message())
        player.sendRawMessage(constructedMessage)
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