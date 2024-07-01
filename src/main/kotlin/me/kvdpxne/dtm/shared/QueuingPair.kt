package me.kvdpxne.dtm.shared

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
}
