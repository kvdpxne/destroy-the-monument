package me.kvdpxne.dtm.container

import java.util.UUID
import me.kvdpxne.dtm.shared.Buildable

/**
 * @since 0.1.0
 */
interface ContainerBuilder<T : ContainerOpener<*>> : Buildable<Container<T>> {

  companion object {

    fun <U : ContainerOpener<*>> begin(): ContainerBuilder<U> {
      return BasicContainerBuilder()
    }
  }

  /**
   * @since 0.1.0
   */
  fun owner(
    owner: UUID
  ): ContainerBuilder<T>

  /**
   * @since 0.1.0
   */
  fun type(
    type: ContainerType
  ): ContainerBuilder<T>

  /**
   * @since 0.1.0
   */
  fun size(
    size: Int
  ): ContainerBuilder<T>

  /**
   * @since 0.1.0
   */
  fun displayName(
    displayName: String
  ): ContainerBuilder<T>

  /**
   * @since 0.1.0
   */
  fun slot(
    index: Byte,
    item: Any,
    handler: SlotHandler<T>? = null
  ): ContainerBuilder<T>

  /**
   * @since 0.1.0
   */
  fun centerSlot(
    item: Any,
    handler: SlotHandler<T>? = null
  ): ContainerBuilder<T>
}