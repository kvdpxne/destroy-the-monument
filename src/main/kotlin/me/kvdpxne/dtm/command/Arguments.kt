package me.kvdpxne.dtm.command

import me.kvdpxne.dtm.game.Arena
import me.kvdpxne.dtm.game.ArenaManager
import me.kvdpxne.dtm.game.Game
import me.kvdpxne.dtm.game.GameManager

/**
 * A class representing a collection of command arguments provided by the user.
 *
 * This class provides methods to access and manipulate the arguments passed
 * to a command. It offers properties for the complete list of arguments
 * (`arguments`), the number of arguments (`size`), and methods to extract
 * specific arguments as strings, integers, or longs.
 *
 * @param arguments An array of strings representing the command arguments.
 *
 * @since 0.1.0
 */
class Arguments(
  arguments: Array<out String>
) {

  /**
   * The internal storage for the command arguments.
   *
   * This property is private to prevent direct modification of the underlying
   * data. Use the provided getter methods to access the arguments safely.
   *
   * @since 0.1.0
   */
  private var _arguments: Array<out String> = arguments

  /**
   * Returns a read-only copy of the complete list of arguments.
   *
   * This method creates a copy of the internal array to ensure modifications
   * to the returned value don't affect the original arguments.
   *
   * @return A copy of the arguments array.
   *
   * @since 0.1.0
   */
  val arguments: Array<out String>
    get() = this._arguments.copyOf()

  /**
   * Returns the number of arguments provided.
   *
   * @return The size of the arguments array.
   *
   * @since 0.1.0
   */
  val size: Int
    get() = this._arguments.size

  /**
   * Creates a new `Arguments` object containing a subsection of the original
   * arguments.
   *
   * This method extracts a portion of the arguments list starting from the
   * specified `from` index (inclusive) and ending at the `to` index
   * (exclusive). It ensures `from` is less than or equal to `to` to avoid
   * unexpected behavior. If `from` is greater than `to`, the original
   * `Arguments` object is returned. The extracted arguments are then used
   * to create a new `Arguments` object.
   *
   * @param from The starting index (inclusive, defaults to 1).
   * @param to   The ending index (exclusive, defaults to the end of the
   *             arguments).
   * @return A new `Arguments` object containing the extracted arguments.
   *
   * @since 0.1.0
   */
  fun asArguments(
    from: Int = 1,
    to: Int = this.size
  ): Arguments {
    if (from > to) {
      return this
    }
    return this._arguments.copyOfRange(from, to).toArguments()
  }

  /**
   * Retrieves the argument at the specified index as a string.
   *
   * This method throws an `ArrayIndexOutOfBoundsException` if the provided
   * index is outside the valid range of arguments.
   *
   * @param index The index of the argument to retrieve (defaults to 0).
   * @throws ArrayIndexOutOfBoundsException if the index is invalid.
   * @return The argument at the specified index as a string.
   *
   * @since 0.1.0
   */
  fun asText(
    index: Int = 0
  ): String {
    return this._arguments[index]
  }

  /**
   * Returns all arguments joined into a single string separated by spaces.
   *
   * This method uses the `joinToString` function with a space delimiter to
   * combine all arguments into a single string.
   *
   * @return A string containing all arguments separated by spaces.
   */
  fun asFullText(): String {
    return this._arguments.joinToString(" ")
  }

  /**
   * Retrieves the argument at the specified index as an integer.
   *
   * This method first converts the argument at the given index to a string
   * using `asText` and then parses it to an integer.
   *
   * It throws an `ArrayIndexOutOfBoundsException` if the index is invalid or
   * a `NumberFormatException` if the argument cannot be parsed as an integer.
   *
   * @param index The index of the argument to retrieve (defaults to 0).
   * @throws ArrayIndexOutOfBoundsException if the index is invalid.
   * @throws NumberFormatException if the argument cannot be parsed as an integer.
   * @return The argument at the specified index as an integer.
   *
   * @since 0.1.0
   */
  fun asInt(
    index: Int = 0
  ): Int {
    return this.asText(index).toInt()
  }

  /**
   * Retrieves the argument at the specified index as a long.
   *
   * This method first converts the argument at the given index to a string
   * using `asText` and then parses it to a long.
   *
   * It throws an `ArrayIndexOutOfBoundsException` if the index is invalid or
   * a `NumberFormatException` if the argument cannot be parsed as a long.
   *
   * @param index The index of the argument to retrieve (defaults to 0).
   * @throws ArrayIndexOutOfBoundsException if the index is invalid.
   * @throws NumberFormatException if the argument cannot be parsed as a long.
   * @return The argument at the specified index as a long.
   *
   * @since 0.1.0
   */
  fun asLong(index: Int = 0): Long {
    return this.asText(index).toLong()
  }

  fun asFoundArena(
    index: Int = 0
  ): Arena? {
    val arenaName = this.asText(index)
    val arena = ArenaManager.findArenaByName(arenaName, true)

    return arena
  }

  fun asFoundGame(
    index: Int = 0
  ): Game? {
    val gameName = this.asText(index)
    val game = GameManager.findByName(gameName, true)

    return game
  }

  /**
   * Checks if there are no arguments provided.
   *
   * This method simply checks if the size of the arguments array is zero.
   *
   * @return True if there are no arguments, false otherwise.
   *
   * @since 0.1.0
   */
  fun isEmpty(): Boolean {
    return 0 == this.size
  }

  fun asFloat(): Float {
    TODO("Not yet implemented")
  }
}