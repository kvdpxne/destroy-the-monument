package me.kvdpxne.dtm.container

/**
 * Utility object for defining and working with container row sizes.
 *
 * The object provides constants representing commonly used row sizes
 * for container interfaces and a method to calculate the nearest valid
 * row size based on a given number.
 *
 * @since 0.1.0
 */
object Rows {

  /**
   * Represents one row (9 slots).
   *
   * @since 0.1.0
   */
  const val ONE: Int = 9

  /**
   * Represents two rows (18 slots).
   *
   * @since 0.1.0
   */
  const val TWO: Int = 18

  /**
   * Represents three rows (27 slots).
   *
   * @since 0.1.0
   */
  const val THREE: Int = 27

  /**
   * Represents four rows (36 slots).
   *
   * @since 0.1.0
   */
  const val FOUR: Int = 36

  /**
   * Represents five rows (48 slots).
   *
   * @since 0.1.0
   */
  const val FIVE: Int = 48

  /**
   * Represents six rows (54 slots).
   *
   * @since 0.1.0
   */
  const val SIX: Int = 54

  /**
   * The minimum allowed row size, equal to [ONE].
   *
   * @since 0.1.0
   */
  const val MINIMUM: Int = this.ONE

  /**
   * The maximum allowed row size, equal to [SIX].
   *
   * @since 0.1.0
   */
  const val MAXIMUM: Int = this.SIX

  /**
   * Finds the nearest valid row size for a given number of slots.
   *
   * The method adjusts the provided number to the nearest multiple of 9,
   * ensuring it represents a valid row configuration within the allowed limits.
   *
   * @param number The number of slots to adjust.
   * @return The nearest valid row size, rounded up to the next multiple of 9
   * if the input is not already a multiple of 9.
   * @throws IllegalArgumentException if the input is negative or
   * exceeds [MAXIMUM].
   * @since 0.1.0
   */
  fun nearestRows(
    number: Int,
    checkMaximum: Boolean = true
  ): Int {
    require(0 <= number) {
      "The passed number must not be negative."
    }

    if (checkMaximum) {
      require(this.MAXIMUM >= number) {
        "The passed number must not be greater than ${this.MAXIMUM}."
      }
    } else {
      // If checking the maximum value is omitted then the passed number
      // greater than or equal to the size of the maximum should return the
      // maximum in this method.
      if (this.MAXIMUM <= number) {
        return this.MAXIMUM
      }
    }

    val remainder: Int = number % 9
    if (0 == remainder) {
      return number
    }

    return number + (9 - remainder)
  }
}