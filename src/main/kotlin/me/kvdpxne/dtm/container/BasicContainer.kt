package me.kvdpxne.dtm.container

import java.util.UUID
import java.util.concurrent.atomic.AtomicInteger
import me.kvdpxne.dtm.shared.debug.Debug
import me.kvdpxne.dtm.shared.reflection.ConstructorInvoker
import me.kvdpxne.dtm.shared.reflection.Reflection
import me.kvdpxne.dtm.shared.text.toSingleLines

/**
 * Represents a container with slots that can hold items, and provides
 * functionality for interacting with these items and managing the container.
 *
 * @param T The type of items in the container.
 * @param T The type of container opener that can open the container.
 * @param V The type of items that can be inserted into slots.
 *
 * @since 0.1.0
 */
open class BasicContainer<T : ContainerOpener<*>>(
  // @formatter:off
        override val owner       : UUID,
  final override val size        : Int,
  final override val displayName : String,
                     initialSlots: Iterable<IndexedSlot>? = null
  // @formatter:on
) : Container<T> {

  companion object {

    /**
     * [ConstructorInvoker] for creating `PacketPlayOutOpenWindow` packet.
     *
     * @since 0.1.0
     */
    private val PACKET_PLAY_OUT_OPEN_WINDOW: ConstructorInvoker by lazy {
      val clazz: Class<*> = Reflection.getMinecraftClass("PacketPlayOutOpenWindow")

      Reflection.getConstructor2(
        clazz,
        arrayOf(
          Int::class.java,
          Int::class.java,
          String::class.java,
          Int::class.java,
          Boolean::class.java
        )
      )
    }

    private val PACKET_PLAY_OUT_WINDOW_ITEMS: ConstructorInvoker by lazy {
      val clazz: Class<*> = Reflection.getMinecraftClass("PacketPlayOutWindowItems")

      Reflection.getConstructor2(
        clazz,
        arrayOf(
          Int::class.java,
          List::class.java,
        )
      )
    }

    /**
     * [ConstructorInvoker] for creating `PacketPlayOutSetSlot` packet.
     *
     * @since 0.1.0
     */
    private val PACKET_PLAY_OUT_SET_SLOT: ConstructorInvoker by lazy {
      val clazz: Class<*> = Reflection.getMinecraftClass("PacketPlayOutSetSlot")

      Reflection.getConstructor2(
        clazz,
        arrayOf(
          Int::class.java,
          Int::class.java,
          Reflection.getMinecraftClass("ItemStack")
        )
      )
    }

    /**
     * @since 0.1.0
     */
    private val IDENTIFIER_COUNTER: AtomicInteger by lazy {
      AtomicInteger(Byte.MAX_VALUE.toInt())
    }
  }

  /**
   * Slots in the container, lazy-initialized.
   *
   * @since 0.1.0
   */
  @Suppress("PropertyName")
  protected val _slots: Lazy<MutableMap<Byte, Slot?>> = lazy {
    val map: MutableMap<Byte, Slot?> = HashMap(this.size)
    for (next: Int in 0.rangeUntil(this.size)) {
      map[next.toByte()] = null
    }
    return@lazy map
  }

  init {
    require(0 == this.size % 9) {
      "The passed size must be divided by 9."
    }

    require(32 > this.displayName.length) {
      "The length of the displayed name cannot be longer than 32 characters."
    }

    if (null != initialSlots) {
      for (slot: IndexedSlot in initialSlots) {
        this._slots.value[slot.index] = BasicSlot(slot.item, slot.handler)
      }
    }
  }

  override val identifier: Byte
    get() = IDENTIFIER_COUNTER.get().toByte()

  override val name: String
    get() = "minecraft:generic_9x${this.rows}"

  override val rows: Int
    get() = this.size / 9

  override val slots: Collection<IndexedSlot>
    get() {
      if (!this._slots.isInitialized()) {
        return emptyList()
      }

      return buildList(this.size) {
        for ((key: Byte, value: Slot?) in this@BasicContainer._slots.value) {
          this.add(BasicIndexedSlot(key, value?.item, value?.handler))
        }
      }
    }

  override fun getSlot(
    index: Int
  ): Slot? {
    return this._slots.value[index.toByte()]
  }

  /**
   * Inserts an item into the specified slot and sends a packet to update
   * the container.
   *
   * @param index The index of the slot.
   * @param item The item to insert into the slot.
   * @param opener The opener sending the packet.
   *
   * @since 0.1.0
   */
  private fun insertItem(
    index: Byte,
    item: Any?,
    opener: T
  ) {
    //
    val packet: Any = PACKET_PLAY_OUT_SET_SLOT.invoke(
      this.identifier,
      index,
      item
    )

    opener.sendPacket(packet)
  }

  override fun insertSlot(
    slot: IndexedSlot
  ) {
    this._slots.value[slot.index] = slot

    Debug.log {
      """
        A new slot has been added to the container with the identifier
        "${this.identifier}" at index "${slot.index}".
      """.toSingleLines()
    }
  }

  override fun insertSlotAndUpdate(
    slot: IndexedSlot,
    opener: T
  ) {
    this.insertSlot(slot)
    this.insertItem(slot.index, slot.item, opener)
  }

  override fun removeSlot(
    index: Int
  ) {
    require(0 <= index || 100 > index) {
      "The passed index must be greater than or equal to 0 and less than 100."
    }

    // If the map with slot positions has not yet been initialized then the
    // container does not contain any slots that can be deleted.
    if (this._slots.isInitialized()) {
      return
    }

    this._slots.value.remove(index.toByte())
  }

  override fun removeSlotAndUpdate(
    index: Int,
    opener: T
  ) {
    this.removeSlot(index)
    this.insertItem(index.toByte(), null, opener)
  }

  override fun update(
    opener: T
  ) {
    val packet: Any = PACKET_PLAY_OUT_WINDOW_ITEMS.invoke(
      this.identifier,
      buildList(this.size) {
        for ((key: Byte, slot: Slot?) in this@BasicContainer._slots.value) {
          this.add(key.toInt(), slot?.item)
        }
      }
    )

    opener.sendPacket(packet)
  }

  private fun nextIdentifier(): Byte {
    return IDENTIFIER_COUNTER.getAndUpdate { current: Int ->
      if (100 == current) {
        Byte.MAX_VALUE.toInt()
      } else {
        current - 1
      }
    }.toByte()
  }

  override fun open(
    whom: T
  ) {
    //
    InternalContainerManager.addContainer(this)

    // Packet sent by the server to the client with a request (forcing) to
    // open this container.
    val packet: Any = PACKET_PLAY_OUT_OPEN_WINDOW.invoke(
      this.identifier,
      0,
      this.displayName,
      this.size,
      true
    )

    // Tries to send the packet to the client.
    whom.sendPacket(packet)

    //
    this.update(whom)

    for ((key: Byte, value: Slot?) in this._slots.value) {
      if (null != value) {
        this.insertItem(key, value.item, whom)
      }
    }

    //
    this.nextIdentifier()
  }

  override fun close(
    whom: T
  ) {
    TODO("Not yet implemented")
  }

  override fun copy(): Container<T> {
    TODO("Not yet implemented")
  }
}