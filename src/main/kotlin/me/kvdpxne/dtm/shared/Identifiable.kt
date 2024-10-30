package me.kvdpxne.dtm.shared

import java.io.Serializable

/**
 * Interface representing objects that can be uniquely identified by a
 * serializable value of type `T`.
 *
 * This interface allows for flexibility in the identifier type as long as
 * it implements `Serializable`. Common choices for T could be `String`, `Long`,
 * or custom serializable identifier classes.
 *
 * @param T The type of the identifier, which must be serializable.
 *
 * @since 0.1.0
 */
interface Identifiable<T : Serializable> {

  /**
   * The unique identifier of the object.
   *
   * @since 0.1.0
   */
  val identifier: T
}