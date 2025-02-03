package me.kvdpxne.dtm.state

import me.kvdpxne.dtm.shared.StylishToStringBuilder

/**
 * A thread-safe mutable state implementation used internally to manage a
 * modifiable state.
 *
 * @param initialModified the initial state of the modified flag. Defaults to `true`.
 * @since 0.1.0
 */
open class InternalMutableState internal constructor(
  initialModified: Boolean = true,
) : MutableState {

  /**
   * Indicates whether the state has been modified.
   *
   * @since 0.1.0
   */
  @Volatile
  private var modified: Boolean = initialModified

  /**
   * Checks whether the state has been modified.
   *
   * @return `true` if the state was modified, `false` otherwise.
   * @since 0.1.0
   */
  override fun wasModified(): Boolean {
    return this.modified
  }

  /**
   * Sets the modified flag to the specified value.
   *
   * @param modified the new value of the modified flag.
   * @since 0.1.0
   */
  override fun setModified(
    modified: Boolean
  ) {
    this.modified = modified
  }

  /**
   * Marks the state as modified.
   *
   * @since 0.1.0
   */
  override fun markAsModified() {
    this.modified = true
  }

  /**
   * Unmarks the state as modified, resetting it to `false`.
   *
   * @since 0.1.0
   */
  override fun unmarkAsModified() {
    this.modified = false
  }

  /**
   * Checks equality between this and another object.
   *
   * @param other the object to compare.
   * @return `true` if the objects are equal, `false` otherwise.
   * @since 0.1.0
   */
  override fun equals(
    other: Any?
  ): Boolean {
    if (this === other) {
      return true
    }

    if (other !is InternalMutableState) {
      return false
    }

    if (this.modified != other.modified) {
      return false
    }

    return true
  }

  /**
   * Returns the hash code of this object.
   *
   * @return the hash code based on the `modified` flag.
   * @since 0.1.0
   */
  override fun hashCode(): Int {
    return this.modified.hashCode()
  }

  /**
   * Returns a string representation of the object, including its state.
   *
   * @return a styled string representation.
   * @since 0.1.0
   */
  override fun toString(): String {
    return StylishToStringBuilder()
      .begin("InternalMutableState")
      .add("modified", this.modified)
      .build()
  }
}