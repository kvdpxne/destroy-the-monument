package me.kvdpxne.dtm.statistics

import me.kvdpxne.dtm.state.InternalMutableState
import me.kvdpxne.dtm.capabilities.state.MutableState

/**
 * @since 0.1.0
 */
abstract class IncompleteStatistics protected constructor(
  initialModified: Boolean = true
) : Statistics, MutableState {

  /**
   * @since 0.1.0
   */
  private val state: MutableState by lazy {
    InternalMutableState(initialModified)
  }

  companion object {

    /**
     * @since 0.1.0
     */
    @Suppress("ConstPropertyName")
    private const val serialVersionUID: Long = 2978184180352340992L
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