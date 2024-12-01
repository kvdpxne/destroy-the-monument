package me.kvdpxne.dtm.container

import java.util.UUID
import me.kvdpxne.dtm.shared.Copyable
import me.kvdpxne.dtm.shared.Identifiable
import me.kvdpxne.dtm.shared.Nameable

/**
 * @since 0.1.0
 */
interface Container<T, U : ContainerOpener<T>> :
  Copyable<Container<T, U>>,
  Identifiable<Byte>,
  Nameable,
  Openable<T, U> {

  /**
   * @since 0.1.0
   */
  override val identifier: Byte

  /**
   * @since 0.1.0
   */
  val owner: UUID

  /**
   * @since 0.1.0
   */
  override val name: String

  /**
   * @since 0.1.0
   */
  override val displayName: String

  /**
   * @since 0.1.0
   */
  val size: Int

  /**
   * @since 0.1.0
   */
  val rows: Int

  /**
   * @since 0.1.0
   */
  val slots: Collection<IndexedSlot<T>>

  /**
   * @since 0.1.0
   */
  fun getSlot(index: Int): Slot<T>?

  /**
   * @since 0.1.0
   */
  fun insertSlot(
    slot: IndexedSlot<T>
  )

  /**
   * @since 0.1.0
   */
  fun insertSlotAndUpdate(
    slot: IndexedSlot<T>,
    opener: U
  )

  /**
   * @since 0.1.0
   */
  fun removeSlot(
    index: Int
  )

  /**
   * @since 0.1.0
   */
  fun removeSlotAndUpdate(
    index: Int,
    opener: U
  )

  /**
   * @since 0.1.0
   */
  fun update(opener: U)

  /**
   * @since 0.1.0
   */
  override fun copy(): Container<T, U>
}