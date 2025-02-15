package me.kvdpxne.dtm.container

/**
 * Represents an entity that can be opened and closed by a specified opener.
 *
 * @param T The type of the opener that can interact with the openable object.
 *
 * @since 0.1.0
 */
interface Openable<T : ContainerOpener<*>> {

  /**
   * Opens the object for the specified opener.
   *
   * @param whom The opener requesting to open the object.
   *
   * @since 0.1.0
   */
  fun open(
    whom: T
  )

  /**
   * Closes the object for the specified opener.
   *
   * @param whom The opener requesting to close the object.
   *
   * @since 0.1.0
   */
  fun close(
    whom: T
  )
}