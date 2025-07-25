package me.kvdpxne.dtm

import java.io.Serializable
import me.kvdpxne.dtm.capabilities.Identifiable

/**
 * @since 0.1.0
 */
open class BasicIdentifiable<T : Serializable>(
  identifier: T
) : Identifiable<T> {

  /**
   * @since 0.1.0
   */
  private val identifiable: Identifiable<T> by lazy {
    InternalIdentifiable(identifier)
  }

  /**
   * @since 0.1.0
   */
  override fun getIdentifier(): T {
    return this.identifiable.getIdentifier()
  }

  /**
   * @since 0.1.0
   */
  override fun equals(
    other: Any?
  ): Boolean {
    if (this === other) {
      return true
    }

    if (other !is BasicIdentifiable<*>) {
      return false
    }

    if (this.getIdentifier() != other.getIdentifier()) {
      return false
    }

    return true
  }

  /**
   * @since 0.1.0
   */
  override fun hashCode(): Int {
    return this.getIdentifier().hashCode()
  }
}