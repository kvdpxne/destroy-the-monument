package me.kvdpxne.dtm.container

/**
 * Represents a container slot that can hold an item of a specified type.
 *
 * @param T The type of item that can be stored in the slot.
 *
 * @since 0.1.0
 */
interface Slot {

  /**
   * The item stored in this slot, or `null` if the slot is empty.
   *
   * @since 0.1.0
   */
  val item: Any?

  /**
   * @since 0.1.0
   */
  val handler: SlotHandler<*>?
}