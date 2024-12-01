package me.kvdpxne.dtm.container

/**
 * Represents a container type with a unique identifier and name.
 *
 * @since 0.1.0
 */
interface ContainerType {

  /**
   * The unique type identifier for this container.
   *
   * @since 0.1.0
   */
  val type: Byte

  /**
   * The name of this container type.
   *
   * @since 0.1.0
   */
  val name: CharSequence
}