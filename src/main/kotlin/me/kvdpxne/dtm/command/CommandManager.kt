package me.kvdpxne.dtm.command

import me.kvdpxne.dtm.command.bukkit.BukkitCommandMapAccessor

object CommandManager {

  /**
   * @since 0.1.0
   */
  private val _commands: MutableSet<Command> = mutableSetOf()

  /**
   * @since 0.1.0
   */
  val commands: Set<Command>
    get() = this._commands.toSet()

  /**
   * @since 0.1.0
   */
  val size: Int
    get() = this._commands.size

  internal fun getSubCommand(
    args: Array<out String>,
    currentCommand: Pair<Command, Int>? = null,
    idx: Int = 0
  ): Pair<Command, Int>? {
    // Return the last command when there are no more arguments
    if (idx >= args.size) {
      return currentCommand
    }

    // If currentCommand is null, idx must be 0, so search in all commands
    val commandSupplier = currentCommand?.first?.children?.asIterable() ?: commands

    // Look if something matches the current index, if it does, look if there are further matches
    commandSupplier
      .firstOrNull { it.matches(args[idx]) }
      ?.let { return getSubCommand(args, Pair(it, idx), idx + 1) }

    // If no match was found, currentCommand is the subcommand that we searched for
    return currentCommand
  }

  /**
   * @since 0.1.0
   */
  fun addCommand(command: Command){
    if (this._commands.add(command)) {
      BukkitCommandMapAccessor.registerCommands(command)
    }
  }

  /**
   * @since 0.1.0
   */
  fun addCommands(vararg commands: Command) {
    commands.forEach { this.addCommand(it) }
  }

  /**
   * @since 0.1.0
   */
  fun removeCommand(command: Command) {
    this._commands.remove(command)
  }

  /**
   * @since 0.1.0
   */
  fun isEmpty(): Boolean {
    return this._commands.isEmpty()
  }
}