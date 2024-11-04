package me.kvdpxne.dtm.data.state

/**
 * A base implementation of the [MutableState] interface, providing a concrete
 * way to track the modification status of a state. This class can be used as a
 * foundation for other mutable states that need modification tracking.
 *
 * @property wasModified Tracks whether the state has been modified.
 * Initialized to the value of [initialState] and can be marked or unmarked as
 * modified via [markAsModified] and [unmarkAsModified] functions.
 *
 * @param initialState The initial modification state. Defaults to `true`,
 * indicating that the state is considered modified initially. Set to `false`
 * if the initial state should be considered unmodified.
 *
 * @since 0.1.0
 */
open class BaseMutableState(
  initialState: Boolean = true
) : MutableState {

  /**
   * Indicates whether the state has been modified.
   *
   * This property is initialized to the value of [initialState] and can be
   * changed only through the [markAsModified] and [unmarkAsModified] functions.
   *
   * @since 0.1.0
   */
  final override var wasModified: Boolean = initialState
    private set

  override fun markAsModified() {
    this.wasModified = true
  }

  override fun unmarkAsModified() {
    this.wasModified = false
  }
}