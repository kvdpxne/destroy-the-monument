package me.kvdpxne.dtm.container

/**
 * @param mode
 * @param button
 * @param slot
 *
 * @since 0.1.0
 */
enum class ClickType(
  // @formatter:off
  val mode  : Byte,
  val button: Byte,
  val slot  : Int? = null
  // @formatter:on
) {

  LEFT_MOUSE_CLICK(0, 0, null),
  RIGHT_MOUSE_CLICK(0, 1, null),
  SHIFT_LEFT_MOUSE_CLICK(1, 0, null),
  SHIFT_RIGHT_MOUSE_CLICK(1, 1, null),
  NUMBER_KEY_1(2, 0, null),
  NUMBER_KEY_2(2, 1, null),
  NUMBER_KEY_3(2, 2, null),
  NUMBER_KEY_4(2, 3, null),
  NUMBER_KEY_5(2, 4, null),
  NUMBER_KEY_6(2, 5, null),
  NUMBER_KEY_7(2, 6, null),
  NUMBER_KEY_8(2, 7, null),
  NUMBER_KEY_9(2, 8, null),
  MIDDLE_CLICK(3, 2, null),
  DROP_KEY(4, 0, null),
  CTRL_DROP_KEY(4, 1, null),
  LEFT_CLICK_OUTSIDE_INVENTORY_HOLDING_NOTHING(4, 0, SlotTypes.OUTSIDE),
  RIGHT_CLICK_OUTSIDE_INVENTORY_HOLDING_NOTHING(4, 1, SlotTypes.OUTSIDE),
  STARTING_LEFT_MOUSE_DRAG(5, 0, SlotTypes.OUTSIDE),
  STARTING_RIGHT_MOUSE_DRAG(5, 4, SlotTypes.OUTSIDE),
  ADD_SLOT_FOR_LEFT_MOUSE_DRAG(5, 1, null),
  ADD_SLOT_FOR_RIGHT_MOUSE_DRAG(5, 5, null),
  ENDING_LEFT_MOUSE_DRAG(5, 2, SlotTypes.OUTSIDE),
  ENDING_RIGHT_MOUSE_DRAG(5, 6, SlotTypes.OUTSIDE),
  DOUBLE_CLICK(6, 0, null);

  /**
   * @since 0.1.0
   */
  fun isOutside(): Boolean {
    return null != this.slot && SlotTypes.OUTSIDE == this.slot
  }

  /**
   * @since 0.1.0
   */
  fun isInside(): Boolean {
    return null == this.slot
  }
}