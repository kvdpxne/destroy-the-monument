package me.kvdpxne.dtm.shared

/**
 * Defines a contract for objects that possess a unique identifier and
 * potentially a separate display name.
 *
 * This interface allows for consistent representation of named entities
 * within the application.
 *
 * Subclasses implementing `Nameable` must provide both a unique `name` and
 * an optional `displayName`. The `displayName` is intended for user-friendly
 * presentation, potentially differing from the internal identifier.
 *
 * @since 0.1.0
 */
interface Nameable {

  /**
   * Retrieves the unique identifier name of the object.
   *
   * This property represents the internal identifier used within the
   * application to uniquely identify the object.
   *
   * @return The unique identifier name of the object.
   *
   * @since 0.1.0
   */
  val name: String

  /**
   * Retrieves the display name of the object, or the `name` if no display
   * name is set.
   *
   * This property provides the name intended for user-friendly presentation.
   * It might be the same as the `name` or a more descriptive version suitable
   * for user interfaces.
   *
   * @return The display name of the object, or the `name` if no display name
   *         is set.
   *
   * @since 0.1.0
   */
  val displayName: String

  /**
   * Checks if the object has a dedicated display name different from its
   * `name`.
   *
   * This method determines whether a separate display name has been configured
   * for the object.
   *
   * @return True if the object has a dedicated display name, false otherwise.
   *
   * @since 0.1.0
   */
  val hasDisplayName: Boolean
    get() = this.name != this.displayName
}