package me.kvdpxne.dtm.command

/**
 * @since 0.1.0
 */
internal class CommandImpl<T : Performer> internal constructor(
  // @formatter:off
  override val name       : String,
  override val description: String,
  override var usage      : String,
  override val aliases    : Array<String>,
  override val permission : String,
  override val executable : Boolean,
  override val parameters : Array<Parameter<*>>,
  override val children   : Array<Command<Performer>>,
  override val handler    : CommandHandler<T>?,
  // @formatter:on
) : Command<T> {

  /**
   * @since 0.1.0
   */
  internal var _parent: CommandImpl<Performer>? = null

  init {

    var usageMessage = ""
    //
    for (parameter: Parameter<*> in this.parameters) {
      if (parameter !is ParameterImpl<*>) {
        continue
      }

      require(null == parameter._command) {
        ""
      }

      parameter._command = this

      if (usageMessage.isNotEmpty()) {
        usageMessage += " "
      }

      usageMessage += if (parameter.required) {
        "<${parameter.name}>"
      } else {
        "[${parameter.name}]"
      }
    }

    //
    for (child: Command<Performer> in this.children) {
      if (child !is CommandImpl<Performer>) {
        continue
      }

      require(null == child._parent) {
        "Child command cannot have the same parent."
      }

      @Suppress("UNCHECKED_CAST")
      child._parent = this as CommandImpl<Performer>
    }

    this.usage = "/${this.fullName} $usageMessage"
  }

  companion object {

    /**
     * @param commands
     * @param suggestions
     * @param label
     *
     * @since 0.1.0
     */
    private fun fillSuggestionsWithNames(
      commands: Array<Command<Performer>>,
      suggestions: MutableList<String>,
      label: String
    ) {
      for (command: Command<Performer> in commands) {
        if (command.name.startsWith(label, true)) {
          suggestions.add(command.name)
        }

        for (alias: String in command.aliases) {
          if (alias.startsWith(label, true)) {
            suggestions.add(alias)
          }
        }

        if (suggestions.isEmpty()) {
          this.fillSuggestionsWithNames(command.children, suggestions, label)
        }
      }
    }
  }

  /**
   * @since 0.1.0
   */
  override val fullName: String
    get() {
      val parent: Command<Performer> = this._parent ?: return this.name
      return "${parent.fullName} ${this.name}"
    }

  /**
   * @since 0.1.0
   */
  override val parent: Command<Performer>?
    get() = this._parent!!

  /**
   * @since 0.1.0
   */
  override fun matches(
    input: String
  ): Boolean {
    if (this.name.equals(input, true)) {
      return true
    }

    for (alias: String in this.aliases) {
      if (alias.equals(input, true)) {
        return true
      }
    }

    return false
  }

  /**
   * The routine that handles the parsing of a single parameter
   */
  private fun parseParameter(argument: String, parameter: Parameter<*>): Any {
    return if (parameter.validatorHandler == null) {
      argument
    } else {
      val validationResult = parameter.validatorHandler!!.invoke(argument)

      if (validationResult.errorMessage != null) {
        throw CommandException("")
//          translation(
//            "liquidbounce.commandManager.invalidParameterValue",
//            parameter.name,
//            argument,
//            validationResult.errorMessage
//          ),
//          usageInfo = command.usage()
//        )
      }

      validationResult.mappedResult!!
    }
  }

  override fun execute(
    performer: T,
    arguments: Array<String>
  ) {
    // Prevent bugs
    if (arguments.isEmpty()) {
      return
    }

    // getSubcommands will only return null if it returns on the first index.
    // since the first index must contain a valid command, it is reported as
    // unknown
    val pair = CommandManager.getSubCommand(arguments)
    if (null == pair) {
      performer.sendMessage("Command ${arguments[0]} was not found.")
      return
    }

    val command = pair.first

    // If the command is not executable, don't allow it to be executed
    if (!command.executable) {
      performer.sendMessages(
        "&7Incorrect use of the &c${command.name} &7command.",
        "&7Usage: &a${command.usage}"
      )
      return
    }

    // The index the command is in
    val idx = pair.second

    // If there are more arguments for a command that takes no parameters
    if (command.parameters.isEmpty() && idx != arguments.size - 1) {
      performer.sendMessages(
        "&7The command does not accept any arguments.",
        "&7Usage: &a${command.usage}"
      )
      return
    }

    // If there is a required parameter after the supply of arguments ends, it is absent
    if (arguments.size - idx - 1 < command.parameters.size && command.parameters[arguments.size - idx - 1].required) {
      performer.sendMessages(
        "Parameter ${command.parameters[arguments.size - idx - 1].name} is required.",
        "&7Usage: &a${command.usage}"
      )
      return
    }

    // The values of the parameters. One for each parameter
    val parsedParameters = arrayOfNulls<Any>(arguments.size - idx - 1)

    // If the last parameter is a vararg, there might be no argument for it.
    // In this case it's value might be null which is against the specification.
    // To fix this, if the last parameter is a vararg, initialize it with an empty array
    if (true == command.parameters.lastOrNull()?.varargs) {
      parsedParameters[command.parameters.size - 1] = emptyArray<Any>()
    }

    for (i in (idx + 1) until arguments.size) {
      val paramIndex = i - idx - 1

      // Check if there is a parameter for this index
      if (paramIndex >= command.parameters.size) {
        performer.sendMessages(
          "Unknown parameter ${arguments[i]}",
          "&7Usage: &a${command.usage}"
        )
        return
      }

      val parameter = command.parameters[paramIndex]

      // Special treatment for varargs
      val parameterValue = if (parameter.varargs) {
        val outputArray = arrayOfNulls<Any>(arguments.size - i)

        for (j in i until arguments.size) {
          outputArray[j - i] = parseParameter(arguments[j], parameter)
        }

        outputArray
      } else {
        parseParameter(arguments[i], parameter)
      }

      // Store the parsed value in the parameter array
      parsedParameters[paramIndex] = parameterValue

      // Vararguments can only occur at the end and the following arguments shouldn't be treated
      // as parameters, so we can end
      if (parameter.varargs) {
        break
      }
    }

    if (!command.executable) {
      performer.sendMessages(
        "&7The command &c${command.name} &7is not executable.",
        "&7Usage: &a${command.usage}"
      )
      return
    }

    @Suppress("UNCHECKED_CAST")
    command.handler!!(performer, parsedParameters as Array<Any>)
  }

  override fun suggestions(
    suggestions: MutableList<String>,
    arguments: Array<String>,
    commandIndex: Int
  ) {
    val lastArgument: String = arguments.last()

    if (this.children.isEmpty()) {

      //
      if (this.parameters.isEmpty()) {
        return
      }

      val parameter: SuggestionHandler = this.parameters[commandIndex - 1].suggestionHandler
        ?: return

      suggestions.addAll(parameter(lastArgument))
      return
    }

    fillSuggestionsWithNames(this.children, suggestions, lastArgument)
  }

  override fun toString(): String {
    return "Command{" +
      "name=\"${this.name}\", " +
      "description=\"${this.description}\", " +
      "usage=\"${this.usage}\", " +
      "aliases=[\"${this.aliases.joinToString()}\"], " +
      "permission=\"${this.permission}\", " +
      "executable=\"${this.executable}\", " +
      "parameters=\"${this.parameters}\", " +
      "children=\"${this.children.contentToString()}\"" +
      "}"
  }
}