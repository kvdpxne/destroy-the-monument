package me.kvdpxne.dtm.shared

import java.io.Serializable

/**
 * An abstract base class for objects that require a unique identifier.
 *
 * This class provides a foundation for implementing identifiable objects
 * within the application. Subclasses must extend this class and provide
 * a unique identifier of a serializable type (`T`). The `identifier`
 * property is used for equality and hash code generation.
 *
 * @param T The type of the unique identifier (must be serializable).
 * @param identifier The unique identifier for the object.
 *
 * @since 0.1.0
 */
abstract class AbstractIdentifiable<T : Serializable>(
  override val identifier: T
) : Identifiable<T> {


  /**
   * Compares this object with another for equality.
   *
   * Two objects are considered equal if they are of the same type and have
   * the same identifier.
   *
   * @param other The object to be compared with.
   * @return True if the objects are equal, false otherwise.
   *
   * @since 0.1.0
   */
  override fun equals(
    other: Any?
  ): Boolean {
    if (this === other) {
      return true
    }

    if (this.javaClass != other?.javaClass) {
      return false
    }

    other as AbstractIdentifiable<*>

    return this.identifier == other.identifier
  }

  /**
   * Generates a hash code for this object.
   *
   * The hash code is based on the object's identifier.
   *
   * @return The hash code of this object.
   *
   * @since 0.1.0
   */
  override fun hashCode(): Int {
    return this.identifier.hashCode()
  }
}