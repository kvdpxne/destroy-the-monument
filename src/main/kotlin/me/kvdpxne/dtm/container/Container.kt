package me.kvdpxne.dtm.container

import java.util.UUID
import me.kvdpxne.dtm.shared.Copyable
import me.kvdpxne.dtm.shared.Identifiable
import me.kvdpxne.dtm.shared.Nameable

/**
 * @since 0.1.0
 */
interface Container<U : ContainerOpener<*>> :
  Copyable<Container<U>>,
  Identifiable<Byte>,
  Nameable,
  Openable<U> {

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
  val slots: Collection<IndexedSlot>

  /**
   * @since 0.1.0
   */
  fun getSlot(index: Int): Slot?

  /**
   * @since 0.1.0
   */
  fun insertSlot(
    slot: IndexedSlot
  )

  /**
   * @since 0.1.0
   */
  fun insertSlotAndUpdate(
    slot: IndexedSlot,
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
  override fun copy(): Container<U>
}