package me.kvdpxne.dtm.game

/**
 * The [GameStates] object provides constants representing the different stages
 * or states in the lifecycle of a game, allowing the game's progress to be
 * tracked and managed.
 *
 * @since 0.1.0
 */
object GameStates {

  /**
   * The state where the game has been initialized and is ready to be started.
   *
   * @since 0.1.0
   */
  const val INITIALIZED = 2

  /**
   * The state where the game is in the process of starting up, handling any
   * initialization tasks such as data loading or environment setup.
   *
   * @since 0.1.0
   */
  const val STARTING = 1

  /**
   * The state where the game is actively running, and players can interact
   * with it.
   *
   * @since 0.1.0
   */
  const val RUNNING = 0

  /**
   * The state where the game is winding down, possibly handling tasks needed
   * before ending.
   *
   * @since 0.1.0
   */
  const val ENDING = 4

  /**
   * The state where the game is shutting down, performing any final tasks such
   * as data saving or resource cleanup.
   *
   * @since 0.1.0
   */
  const val STOPPING = 3
}