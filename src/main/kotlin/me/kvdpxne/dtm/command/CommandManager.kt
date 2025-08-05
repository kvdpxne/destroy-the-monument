package me.kvdpxne.dtm.command

import java.util.Collections

object CommandManager : Iterable<Command<Performer>> {

  private val _commands: MutableMap<String, Command<Performer>> = mutableMapOf()

  val commands: Collection<Command<Performer>>
    get() = Collections.unmodifiableCollection(this._commands.values)

  val names: Collection<String>
    get() = Collections.unmodifiableCollection(this._commands.keys)

  val size: Int
    get() = this._commands.size

  internal fun getSubCommand(
    args: Array<out String>,
    currentCommand: Pair<Command<Performer>, Int>? = null,
    idx: Int = 0
  ): Pair<Command<Performer>, Int>? {
    // Return the last command when there are no more arguments
    if (idx >= args.size) {
      return currentCommand
    }

    // If currentCommand is null, idx must be 0, so search in all commands
    val commandSupplier = currentCommand?.first?.children?.asIterable()
      ?: this._commands.values

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
  fun addCommand(
    command: Command<*>
  ) {
    @Suppress("UNCHECKED_CAST")
    command as Command<Performer>

    this._commands[command.name.lowercase()] = command
    BukkitCommandHandler(command).register()
  }

  /**
   * @since 0.1.0
   */
  fun addCommands(
    vararg commands: Command<*>
  ) {
    for (command: Command<*> in commands) {
      this.addCommand(command)
    }
  }

  fun removeCommand(
    command: Command<Performer>
  ) {
    this._commands.remove(command.name.lowercase())
    // TODO unregister
  }

  /**
   *
   */
  override fun iterator(): Iterator<Command<Performer>> {
    return this._commands.values.iterator()
  }
}