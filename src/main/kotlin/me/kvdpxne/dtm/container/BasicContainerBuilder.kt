package me.kvdpxne.dtm.container

import java.util.UUID
import me.kvdpxne.dtm.shared.text.toSingleLines

open class BasicContainerBuilder<T : ContainerOpener<*>> :
  ContainerBuilder<T> {

  // @formatter:off
  var owner      : UUID?   = null
  var type       : Byte?   = null
  var size       : Byte?   = null
  var displayName: String? = null
  var slots      : MutableList<IndexedSlot>? = null
  // @formatter:on

  override fun owner(owner: UUID): ContainerBuilder<T> {
    this.owner = owner
    return this
  }

  override fun type(type: ContainerType): ContainerBuilder<T> {
    return this
  }

  override fun size(
    size: Int
  ): ContainerBuilder<T> {
    require(Rows.MINIMUM <= size && Rows.MAXIMUM >= size) {
      """
        The passed size must be greater than or equal to "${Rows.MINIMUM}" and
        less than or equal to ${Rows.MAXIMUM}.
      """.toSingleLines()
    }

    this.size = size.toByte()
    this.slots = ArrayList(size)

    return this
  }

  override fun displayName(
    displayName: String
  ): ContainerBuilder<T> {
    require(32 >= displayName.length) {
      "The passed display name must be shorter than or equal to 32 characters."
    }

    this.displayName = displayName
    return this
  }

  override fun slot(
    index: Byte,
    item: Any,
    handler: SlotHandler<T>?
  ): ContainerBuilder<T> {
    checkNotNull(this.slots) {
      "The container size must be defined before adding slots."
    }

    val slot: IndexedSlot = BasicIndexedSlot(index, item, handler)
    this.slots?.add(slot)

    return this
  }

  override fun centerSlot(
    item: Any,
    handler: SlotHandler<T>?
  ): ContainerBuilder<T> {
    val currentSize: Byte = checkNotNull(this.size) {
      "The container size must be defined before adding slots."
    }

    return this.slot((currentSize / 2).toByte(), item, handler)
  }

  override fun build(): Container<T> {
    return BasicContainer(
      this.owner!!,
      (this.size ?: 9).toInt(),
      this.displayName ?: "test",
      this.slots
    )
  }
}