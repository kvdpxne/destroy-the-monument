package me.kvdpxne.dtm.gui

enum class Rows(val size: Int) {

  ONE(9),
  TWO(18),
  THREE(27),
  FOUR(36),
  FIVE(48),
  SIX(54);

  companion object {

    /**
     * Tries to find an instance of a [Rows] object that matches the given
     * [size], if the given [size] is greater than the largest size in the
     * [Rows] object, the method will return the [Rows] instance with the
     * largest [Rows.size].
     *
     * @throws IllegalArgumentException If the given parameter [size] is less
     * than or equal to zero.
     */
    fun findRowBySize(
      size: Int,
      default: () -> Rows = {
        entries.maxBy {
          it.size
        }
      }
    ): Rows = require(0 <= size) {
      "The size can not be smaller than or equal to zero."
    }.let {
      entries.find {
        it.size >= size
      } ?: default()
    }
  }
}