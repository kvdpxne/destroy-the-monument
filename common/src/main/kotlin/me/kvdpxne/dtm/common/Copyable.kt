package me.kvdpxne.dtm.common

/**
 * Represents an object that can be copied.
 *
 * Implementing this interface indicates that an object supports a mechanism to
 * create a copy of itself, typically a deep copy, ensuring the new object is
 * independent of the original.
 *
 * @param T The type of the object that can be copied.
 *
 * @since 0.1.0
 */
interface Copyable<T> {

  /**
   * Creates a copy of the object.
   *
   * The returned copy should ideally be a deep copy, meaning any references to
   * mutable objects within the original should not be shared with the copy.
   * This ensures that modifications to the copy do not affect the original
   * object and vice versa.
   *
   * @return A new instance of type [T] that is a copy of the current object.
   * @since 0.1.0
   */
  fun copy(): T
}