package me.kvdpxne.dtm.container

import java.util.UUID
import me.kvdpxne.dtm.shared.Buildable

/**
 * @since 0.1.0
 */
interface ContainerBuilder<T, U : ContainerOpener<T>> : Buildable<Container<T, U>> {

  companion object {

    fun <T, U : ContainerOpener<T>> begin(): ContainerBuilder<T, U> {
      return BasicContainerBuilder()
    }
  }

  fun owner(
    owner: UUID
  ): ContainerBuilder<T, U>

  /**
   * @since 0.1.0
   */
  fun type(
    type: ContainerType
  ): ContainerBuilder<T, U>

  /**
   * @since 0.1.0
   */
  fun size(
    size: Int
  ): ContainerBuilder<T, U>

  /**
   * @since 0.1.0
   */
  fun displayName(
    displayName: String
  ): ContainerBuilder<T, U>

  /**
   * @since 0.1.0
   */
  fun slot(
    index: Byte,
    item: Any,
    handler: SlotHandler<T>? = null
  ): ContainerBuilder<T, U>
}