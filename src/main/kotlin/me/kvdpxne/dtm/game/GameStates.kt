package me.kvdpxne.dtm.game

/**
 * The [GameStates] object provides a set of constants representing the
 * different stages or states of a game's lifecycle. These constants are used
 * to track and manage the game's progression.
 *
 * @since 0.1.0
 */
object GameStates {

  /**
   * Represents the state where the game has been initialized and is ready to
   * be started.
   *
   * @since 0.1.0
   */
  const val INITIALIZED = 2

  /**
   * Represents the state where the game is transitioning from initialization
   * to running. Processes like loading data or preparing the environment might
   * occur here.
   *
   * @since 0.1.0
   */
  const val STARTING = 1

  /**
   * Represents the state where the game is actively running and players can
   * interact with it.
   *
   * @since 0.1.0
   */
  const val RUNNING = 0

  /**
   * Represents the state where the game is shutting down. Processes like
   * saving data or cleaning up resources might occur here.
   *
   * @since 0.1.0
   */
  const val STOPPING = 3
}