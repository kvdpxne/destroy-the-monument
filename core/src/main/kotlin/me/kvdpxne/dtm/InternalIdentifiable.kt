package me.kvdpxne.dtm

import java.io.Serializable

class InternalIdentifiable<T : Serializable> internal constructor(
  private val identifier: T
) : Identifiable<T> {

  override fun getIdentifier(): T {
    return this.identifier
  }

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (other !is InternalIdentifiable<*>) return false

    if (this.identifier != other.identifier) return false

    return true
  }

  override fun hashCode(): Int {
    return this.identifier.hashCode()
  }
}