package me.kvdpxne.dtm.data.state

import java.io.Serializable
import me.kvdpxne.dtm.shared.Identifiable

open class BaseIdentifiableMutableState<T : Serializable>(
  override val identifier: T
) : BaseMutableState(), Identifiable<T> {

  override fun equals(other: Any?): Boolean {
    if (this === other) {
      return true
    }

    if (this.javaClass != other?.javaClass) {
      return false
    }

    other as BaseIdentifiableMutableState<*>
    return this.identifier == other.identifier
  }

  override fun hashCode(): Int {
    return this.identifier.hashCode()
  }
}