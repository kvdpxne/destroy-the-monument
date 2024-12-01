package me.kvdpxne.dtm.container

/**
 * @since 0.1.0
 */
interface IndexedSlot<T> : Slot<T> {

  /**
   * The index of this slot within the container.
   *
   * @since 0.1.0
   */
  val index: Byte
}