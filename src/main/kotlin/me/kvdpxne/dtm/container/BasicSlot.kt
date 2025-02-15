package me.kvdpxne.dtm.container

/**
 * @param item
 * @param handler
 *
 * @since 0.1.0
 */
open class BasicSlot(
  // @formatter:off
  final override val item   : Any?            = null,
  final override val handler: SlotHandler<*>? = null
  // @formatter:on
) : Slot {

  init {
    if (null != this.item) {
      // Checks if the passed item is an NMS ItemStack instance.
      requireMinecraftItemStack(this.item)
    }

//    require(null != this.handler && null != this.item) {
//      "The passed handler cannot be called if the passed item is equal to null."
//    }
  }

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as BasicSlot

    if (item != other.item) return false
    if (handler != other.handler) return false

    return true
  }

  override fun hashCode(): Int {
    var result = item?.hashCode() ?: 0
    result = 31 * result + (handler?.hashCode() ?: 0)
    return result
  }


}