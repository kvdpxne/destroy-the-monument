package me.kvdpxne.dtm.command

object CommandManager : Iterable<Command> {

  /**
   * A set of all registered commands.
   */
  var commands: MutableList<Command> = mutableListOf()
    private set

  fun addCommand(command: Command): Boolean {
    val contents = buildList(16) {
      this.add(command.name)
      this.addAll(command.aliases)
    }

    val result = commands.any { command2 ->
      contents.any {
        command2.contains(it)
      }
    }

    if (result) {
      return false
    }

    this.commands.add(command)
    return true
  }

  fun removeCommand(command: Command): Boolean {
    return commands.remove(command)
  }

  fun removeCommand(content: String): Boolean {
    return commands.removeIf {
      it.contains(content)
    }
  }

  fun size(): Int {
    return commands.size
  }

  override fun iterator(): Iterator<Command> {
    return this.commands.iterator()
  }
}