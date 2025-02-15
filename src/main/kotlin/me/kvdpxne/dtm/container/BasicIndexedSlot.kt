package me.kvdpxne.dtm.container

/**
 * @param index
 * @param item
 * @param handler
 *
 * @since 0.1.0
 */
class BasicIndexedSlot(
  // @formatter:off
  override val index  : Byte,
               item   : Any?            = null,
               handler: SlotHandler<*>? = null
  // @formatter:on
) : BasicSlot(item, handler), IndexedSlot {

  init {
    require(0 <= this.index) {
      "The passed index must be greater than or equal to 0."
    }
  }

  override fun equals(other: Any?): Boolean {
    if (this === other) {
      return true
    }

    if (this.javaClass != other?.javaClass) {
      return false
    }

    other as BasicIndexedSlot
    return this.index == other.index
  }

  override fun hashCode(): Int {
    return this.index.toInt()
  }


}