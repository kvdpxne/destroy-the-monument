package me.kvdpxne.dtm.command

import me.kvdpxne.dtm.shared.ancillary.Buildable

class CommandBuilder<T : Performer> private constructor(
  private val name: String
): Buildable<Command<T>> {

  // @formatter:off
  private var description: String?
  private var usage      : String?
  private var aliases    : Array<String>?
  private var permission : String?
  private var executable : Boolean
  private val parameters : MutableList<Parameter<*>>
  private val children   : MutableList<Command<Performer>>
  private var handler    : CommandHandler<T>?
  // @formatter:on

  init {
    this.description = null
    this.usage = null
    this.aliases = null
    this.permission = null
    this.executable = true
    this.parameters = mutableListOf()
    this.children = mutableListOf()
    this.handler = null
  }

  companion object {

    /**
     * @since 0.1.0
     */
    fun <T : Performer> begin(
      name: String
    ): CommandBuilder<T> {
      return CommandBuilder(name)
    }
  }

  fun description(description: String): CommandBuilder<T> {
    this.description = description
    return this
  }

  fun usage(usage: String): CommandBuilder<T> {
    this.usage = usage
    return this
  }

  fun aliases(vararg aliases: String): CommandBuilder<T> {
    this.aliases = arrayOf(*aliases)
    return this
  }

  fun permission(permission: String): CommandBuilder<T> {
    this.permission = permission
    return this
  }

  fun hub(): CommandBuilder<T> {
    this.executable = false
    return this
  }

  fun parameter(parameter: Parameter<*>): CommandBuilder<T> {
    this.parameters += parameter
    return this
  }

  fun children(vararg child: Command<*>): CommandBuilder<T> {
    child.forEach {
      this.children += it as Command<Performer>
    }
    return this
  }

  fun handler(handler: CommandHandler<T>): CommandBuilder<T> {
    this.handler = handler
    return this
  }

  override fun build(): Command<T> {
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
      isVarargs = it.varargs
    }

    return CommandImpl(
      this.name,
      this.description ?: "",
      this.usage ?: "",
      this.aliases ?: emptyArray(),
      this.permission ?: "dtm.command.${this.name}",
      this.executable,
      this.parameters.toTypedArray(),
      this.children.toTypedArray(),
      this.handler
    )
  }
}