package me.kvdpxne.dtm.game

enum class GameState {

  /**
   * The game has been initialized, it is neither started nor stopped, it is
   * getting ready to start or stop, but it doesn't have to be started at all.
   */
  INITIALIZED,

  /**
   * The game is now starting.
   */
  STARTING,

  /**
   * The game has now started and is running.
   */
  STARTED,

  /**
   * The game is now ending.
   */
  STOPPING,

  /**
   * The game has stopped or ended.
   */
  STOPPED
}