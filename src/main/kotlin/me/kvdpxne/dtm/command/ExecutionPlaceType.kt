package me.kvdpxne.dtm.command

/**
 * Defines the possible places where a command can be executed.
 */
enum class ExecutionPlaceType {

  /**
   * Indicates the command should be executed within the console.
   */
  IN_CONSOLE,

  /**
   * Indicates the command should be executed inside the game itself.
   */
  IN_GAME,

  /**
   * Indicates the command should be executed specifically within the game
   * world (e.g., within a game map).
   */
  IN_GAME_WORLD,

  /**
   * Indicates the command can be executed anywhere, regardless of location
   * (console, game, game world).
   */
  EVERYWHERE
}