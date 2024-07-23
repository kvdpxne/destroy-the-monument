package me.kvdpxne.dtm.shared.ancillary

/**
 * @param initialState
 *
 *
 */
open class BaseMutable protected constructor(
  initialState: Boolean = true
) : Mutable {

  /**
   *
   */
  var state: Boolean = initialState
    protected set

  /**
   *
   */
  override val wasChanged: Boolean
    get() = this.state

  /**
   *
   */
  fun markChanged() {
    this.state = true
  }

  fun markUnchanged() {
    this.state = false
  }

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as BaseMutable

    return state == other.state
  }

  override fun hashCode(): Int {
    return state.hashCode()
  }
}