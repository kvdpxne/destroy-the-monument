package me.kvdpxne.dtm.state

import java.io.Serializable
import me.kvdpxne.dtm.BasicIdentifiable
import me.kvdpxne.dtm.capabilities.state.MutableState

open class BasicMutableIdentifiable<T : Serializable>(
  // @formatter:off
  initialModified: Boolean = false,
  identifier     : T
  // @formatter:on
) :
  BasicIdentifiable<T>(identifier),
  MutableState {

  private val state: MutableState by lazy {
    InternalMutableState(initialModified)
  }

  override fun wasModified(): Boolean {
    return this.state.wasModified()
  }

  override fun setModified(
    modified: Boolean
  ) {
    this.state.setModified(modified)
  }

  override fun markAsModified() {
    this.state.markAsModified()
  }

  override fun unmarkAsModified() {
    this.state.unmarkAsModified()
  }
}