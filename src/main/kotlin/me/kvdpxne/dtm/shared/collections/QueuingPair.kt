package me.kvdpxne.dtm.shared.collections

/**
 * Represents a pair of elements where one element is considered `current`
 * and the other is `next`.
 *
 * @param T The type of elements stored in the pair.
 *
 * @param current The initial `current` element.
 * @param next The optional `next` element (defaults to `null`).
 */
class QueuingPair<T>(current: T, next: T? = null) {

  /**
   * The current element in the pair.
   */
  var current: T
    private set

  /**
   * The next element in the pair, or `null` if there's no next element.
   */
  var next: T?

  init {
    this.current = current
    this.next = next
  }

  /**
   * Checks if there is a next element in the `QueuingPair`.
   *
   * @return `true` if there is a next element, `false` otherwise.
   */
  fun hasNext(): Boolean {
    return null != this.next
  }

  /**
   * Shifts the pair by moving the `next` element to become the new
   * `current` element.
   *
   * @throws NoSuchElementException if there is no next element.
   */
  fun shift() {
    if (!this.hasNext()) {
      throw NoSuchElementException("No next element found.")
    }

    this.current = this.next!!
    this.next = null
  }

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as QueuingPair<*>

    if (current != other.current) return false
    if (next != other.next) return false

    return true
  }

  override fun hashCode(): Int {
    var result = current?.hashCode() ?: 0
    result = 31 * result + (next?.hashCode() ?: 0)
    return result
  }

  override fun toString(): String {
    return "QueuingPair{" +
      "current=\"${this.current}\", " +
      "next=\"${this.next }\"" +
      "}"
  }
}
