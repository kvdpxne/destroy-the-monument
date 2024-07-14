package me.kvdpxne.dtm.command

import me.kvdpxne.dtm.command.ExecutionPlaceType.EVERYWHERE
import me.kvdpxne.dtm.shared.Buildable

/**
 * @since 0.1.0
 */
class CommandBuilder : Buildable<Command> {

  // @formatter:off
  private var name          : String?                = null
  private var description   : String?                = null
  private var usage         : String?                = null
  private var aliases       : MutableList<String>    = mutableListOf()
  private var permission   : String?                = null
  private var executionPlace: ExecutionPlaceType     = EVERYWHERE
  private var executable    : Boolean                = true
  private var handler       : CommandHandler<Any>?   = null
  private var parameters    : MutableList<Parameter> = mutableListOf()
  private var children      : MutableList<Command>   = mutableListOf()
  // @formatter:on

  /**
   * @since 0.1.0
   */
  fun name(
    name: String
  ): CommandBuilder {
    this.name = name
    return this
  }

  /**
   * @since 0.1.0
   */
  fun description(
    description: String
  ): CommandBuilder {
    this.description = description
    return this
  }

  /**
   * @since 0.1.0
   */
  fun usage(
    usage: String
  ): CommandBuilder {
    this.usage = usage
    return this
  }

  /**
   * @since 0.1.0
   */
  fun aliases(
    vararg aliases: String
  ): CommandBuilder {
    this.aliases.addAll(aliases)
    return this
  }

  /**
   * @since 0.1.0
   */
  fun permission(permission: String): CommandBuilder {
    this.permission = permission
    return this
  }

  /**
   * @since 0.1.0
   */
  fun executionPlace(
    executionPlace: ExecutionPlaceType
  ): CommandBuilder {
    this.executionPlace = executionPlace
    return this
  }

  /**
   * @since 0.1.0
   */
  fun hub(): CommandBuilder {
    this.executable = false
    return this
  }

  /**
   * @since 0.1.0
   */
  fun <T : Performer> handler(
    handler: CommandHandler<T>
  ): CommandBuilder {
    @Suppress("UNCHECKED_CAST")
    this.handler = handler as CommandHandler<Any>
    return this
  }

  /**
   * @since 0.1.0
   */
  fun parameters(
    vararg parameters: Parameter
  ): CommandBuilder {
    this.parameters.addAll(parameters)
    return this
  }

  fun parameter(
    parameter: Parameter
  ): CommandBuilder {
    this.parameters.add(parameter)
    return this
  }

  /**
   * @since 0.1.0
   */
  fun children(
    vararg children: Command
  ): CommandBuilder {
    this.children.addAll(children)
    return this
  }

  /**
   * @since 0.1.0
   */
  override fun build(): Command {
    check(this.executable || null == this.handler) {
      "The command is marked as not executable, but handler was specified."
    }

    check(!this.executable || null != this.handler) {
      "The command is marked as executable, but no handler was specified."
    }

    var isRequired = false
    var isVarargs = false

    this.parameters.forEach {
      check(!it.required || !isRequired) {
        "Optional parameters are only allowed at the end."
      }

      require(!it.required || !isVarargs) {
        "VarArgs are only allowed at the end"
      }

      isRequired = !it.required
      isVarargs = it.required
    }

    return Command(
      this.name ?: throw IllegalStateException(""),
      this.description,
      this.usage,
      this.aliases.toTypedArray(),
      this.permission,
      this.executionPlace,
      this.handler,
      this.children.toTypedArray(),
      null
    )
  }
}