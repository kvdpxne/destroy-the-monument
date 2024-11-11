package me.kvdpxne.dtm.gui

/**
 * @since 0.1.0
 */
object GuiArrangement {

  /**
   * @since 0.1.0
   */
  val SINGLE: IntArray = intArrayOf(
    0, 8, 1, 7, 2, 6, 3, 5
  )

  /**
   * @since 0.1.0
   */
  val TWO_ITEMS_ONE_ROW: IntArray by lazy {
    intArrayOf(2, 6)
  }

  /**
   * @since 0.1.0
   */
  val THREE_ITEMS_ONE_ROW: IntArray by lazy {
    intArrayOf(2, 4, 6)
  }

  /**
   * @since 0.1.0
   */
  val FOUR_ITEMS_ONE_ROW: IntArray by lazy {
    intArrayOf(1, 3, 5, 7)
  }
}