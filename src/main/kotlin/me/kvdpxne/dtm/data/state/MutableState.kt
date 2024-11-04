package me.kvdpxne.dtm.data.state

/**
 * An interface representing a mutable state that can be marked as modified.
 *
 * Extends the [State] interface, adding functionality to track whether the
 * state has been changed and to reset this modified status as needed.
 *
 * Implementations of this interface are expected to use the [wasModified] flag
 * to indicate when a state has been modified, enabling systems to respond
 * appropriately to changes (e.g., triggering updates, logging changes, etc.).
 *
 * @since 0.1.0
 */
interface MutableState : State {

  /**
   * A flag indicating whether the state has been modified since the last
   * reset.
   *
   * This is useful for tracking changes, especially in contexts where state
   * persistence or synchronization is required.
   *
   * @return `true` if the state was modified; `false` otherwise.
   * @since 0.1.0
   */
  val wasModified: Boolean

  /**
   * Marks the state as modified. This sets [wasModified] to `true`, allowing
   * systems to recognize that a change has occurred.
   *
   * This function should be called whenever a modification is made to the
   * state, ensuring that [wasModified] accurately reflects the current state.
   *
   * @since 0.1.0
   */
  fun markAsModified()

  /**
   * Resets the modification status of the state, setting [wasModified] to
   * `false`.
   *
   * This can be used after the state changes have been acknowledged or
   * processed, allowing the system to monitor for new modifications going
   * forward.
   *
   * @since 0.1.0
   */
  fun unmarkAsModified()
}