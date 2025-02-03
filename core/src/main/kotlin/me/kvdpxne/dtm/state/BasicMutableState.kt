package me.kvdpxne.dtm.state

/**
 * @since 0.1.0
 */
open class BasicMutableState(
  initialModified: Boolean = true
) : MutableState {

  private val state: MutableState by lazy {
    InternalMutableState(initialModified)
  }

  override fun wasModified(): Boolean {
    return this.state.wasModified()
  }

  override fun setModified(
    modified: Boolean
  ) {
    return this.state.setModified(modified)
  }

  override fun markAsModified() {
    return this.state.markAsModified()
  }

  override fun unmarkAsModified() {
    return this.state.unmarkAsModified()
  }

  override fun equals(other: Any?): Boolean {
    if (this === other) {
      return true
    }

    if (other !is BasicMutableState) {
      return false
    }

    if (this.state != other.state) {
      return false
    }

    return true
  }

  override fun hashCode(): Int {
    return this.state.hashCode()
  }
}