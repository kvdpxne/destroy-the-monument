package me.kvdpxne.dtm.command

/**
 * A base class representing a command within the plugin.
 *
 * This class defines the core properties and functionalities for a command.
 * It serves as the foundation for building specific commands within the
 * application.
 *
 * Subclasses can extend this class to implement custom command behavior.
 *
 * @param name          The unique identifier for the command (required).
 * @param description   A textual description of the command's purpose
 *                      (optional).
 * @param usage         A formatted string illustrating how to use the command
 *                      (optional).
 * @param aliases       Alternative names for the command (optional, defaults
 *                      to an empty array).
 * @param permission    The required permission to execute the command
 *                      (optional).
 * @param executionType The location where the command can be executed (defaults
 *                      to `ExecutionPlaceType.EVERYWHERE`).
 * @param executable
 * @param handler       The function that handles the execution of the command
 *                      (required).
 * @param parameters
 * @param children      An array of sub-commands associated with this command
 *                      (optional, defaults to an empty array).
 * @param parent        The parent command in the command hierarchy (optional).
 *
 * @since 0.1.0
 */
open class Command(
  // @formatter:off
  val name          : String,
      description   : String?              = null,
      usage         : String?              = null,
  val aliases       : Array<out String>    = emptyArray(),
      permission    : String?              = null,
  val executionType : ExecutionPlaceType   = ExecutionPlaceType.EVERYWHERE,
  val executable    : Boolean              = true,
  val handler       : CommandHandler<Any>? = null,
  val parameters    : Array<Parameter<*>>  = emptyArray(),
  val children      : Array<Command>       = emptyArray(),
      parent        : Command?             = null
  // @formatter:on
) {

  /**
   * The full description of the command, initialized during object creation.
   *
   * This property ensures a default description is provided if none is
   * specified during construction.
   *
   * @since 0.1.0
   */
  lateinit var description: String
    private set

  /**
   * The formatted usage string for the command, initialized during object
   * creation.
   *
   * This property ensures a default usage string is provided if none is
   * specified during construction.
   *
   * @since 0.1.0
   */
  lateinit var usage: String
    private set

  /**
   * The required permission to execute the command, initialized during object
   * creation.
   *
   * This property ensures a default permission string is generated based on
   * the command's name if none is provided.
   *
   * @since 0.1.0
   */
  lateinit var permission: String
    private set

  /**
   * @since 0.1.0
   */
  var parent: Command? = parent
    internal set

  /**
   * @since 0.1.0
   */
  init {
    //
    this.parameters.forEach {
      require(null == it.command) {
        "Parameter already has command."
      }

      it.command = this
    }

    //
    this.children.forEach {
      require(null == it.parent) {
        "Child command cannot have the same parent."
      }

      it.parent = this
    }

    // Set default description if not provided
    if (description.isNullOrBlank()) {
      this.description = "Description of command"
    }

    // Set default usage if not provided
    if (usage.isNullOrBlank()) {
      this.usage = "usage"
    }

    // Set default permission if not provided
    if (permission.isNullOrBlank()) {
      this.permission = "dtm.command.${this.fullName(".")}"
    }
  }

  /**
   * Constructs the full name of the command, including parent names if
   * applicable.
   *
   * This method recursively traverses the command hierarchy to build a string
   * representing the full name of the command, separated by the provided
   * separator (defaults to a space).
   *
   * @param separator The string used to separate names in the full name
   *                  (defaults to " ").
   * @return The full name of the command, including parent names.
   *
   * @since 0.1.0
   */
  fun fullName(separator: String = " "): String {
    val parent = this.parent ?: return this.name
    val parentFullName = parent.fullName(separator)

    return parentFullName + separator + this.name
  }

  /**
   * Checks if the provided name matches the command's name or any of its
   * aliases.
   *
   * This method performs a case-insensitive comparison to determine if the
   * given name matches the command's name or any of the defined aliases.
   *
   * @param name The name to be compared against the command's name and aliases.
   * @return True if the name matches the command or any alias, false otherwise.
   *
   * @since 0.1.0
   */
  fun matches(
    name: String
  ): Boolean {
    if (this.name.equals(name, true)) {
      return true
    }

    return this.aliases.any {
      it.equals(name, true)
    }
  }

  /**
   * Compares this command object with another object for equality.
   *
   * This method overrides the default `equals` method to ensure two `Command`
   * objects are considered equal if they have the same name and parent command.
   *
   * This is important for proper command identification and execution.
   *
   * @param other The object to be compared with.
   * @return True if the objects are equal, false otherwise.
   *
   * @since 0.1.0
   */
  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as Command

    if (this.name != other.name) return false
    if (this.parent != other.parent) return false

    return true
  }

  /**
   * Generates a hash code for this command object.
   *
   * This method overrides the default `hashCode` method to ensure consistent
   * hash code generation based on the command's name and parent.
   *
   * This is important for efficient storage and retrieval of commands in
   * collections.
   *
   * @return The hash code for this command object.
   *
   * @since 0.1.0
   */
  override fun hashCode(): Int {
    var result = this.name.hashCode()
    result = 31 * result + (this.parent?.hashCode() ?: 0)
    return result
  }
}