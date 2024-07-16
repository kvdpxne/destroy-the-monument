package me.kvdpxne.dtm.command

object CommandExecutor {

  fun execute(
    performer: Performer,
    arguments: Array<out String>
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

//    // The values of the parameters. One for each parameter
//    val parsedParameters = arrayOfNulls<Any>(arguments.size - idx - 1)
//
//    // If the last parameter is a vararg, there might be no argument for it.
//    // In this case it's value might be null which is against the specification.
//    // To fix this, if the last parameter is a vararg, initialize it with an empty array
//    if (true == command.parameters.lastOrNull()?.varargs) {
//      parsedParameters[command.parameters.size - 1] = emptyArray<Any>()
//    }
//
//    for (i in (idx + 1) until arguments.size) {
//      val paramIndex = i - idx - 1
//
//      // Check if there is a parameter for this index
//      if (paramIndex >= command.parameters.size) {
//        performer.sendMessages(
//          "Unknown parameter ${arguments[i]}",
//          "&7Usage: &a${command.usage}"
//        )
//        return
//      }
//
//      val parameter = command.parameters[paramIndex]
//
//      // Special treatment for varargs
//      val parameterValue = if (parameter.varargs) {
//        val outputArray = arrayOfNulls<Any>(arguments.size - i)
//
//        for (j in i until arguments.size) {
//          outputArray[j - i] = parseParameter(arguments[j], parameter)
//        }
//
//        outputArray
//      } else {
//        parseParameter(arguments[i], parameter)
//      }
//
//      // Store the parsed value in the parameter array
//      parsedParameters[paramIndex] = parameterValue
//
//      // Vararguments can only occur at the end and the following arguments shouldn't be treated
//      // as parameters, so we can end
//      if (parameter.varargs) {
//        break
//      }
//    }

    if (!command.executable) {
      performer.sendMessages(
        "&7The command &c${command.name} &7is not executable.",
        "&7Usage: &a${command.usage}"
      )
      return
    }

    command.handler!!(performer, arguments.toArguments())
  }
}