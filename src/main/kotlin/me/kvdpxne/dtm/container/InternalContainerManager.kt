package me.kvdpxne.dtm.container

import java.util.UUID
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap
import org.bukkit.entity.Player

/**
 * @since 0.1.0
 */
internal object InternalContainerManager {

  /**
   * @since 0.1.0
   */
  private val _containers: ConcurrentMap<UUID, Container<*, *>> =
    ConcurrentHashMap()

  fun hasContainer(uuid: UUID) = _containers.containsKey(uuid)

  fun <T : Player> findContainerByIdentifierOrNull(
    identifier: UUID
  ): Container<T, ContainerOpener<T>>? {
    return _containers[identifier] as Container<T, ContainerOpener<T>>?
  }

  fun <T : Player> findContainerByIdentifier(
    identifier: UUID
  ): Container<T, ContainerOpener<T>> {
    return checkNotNull(findContainerByIdentifierOrNull(identifier)) {
      ""
    }
  }

  fun addContainer(
    container: Container<*, *>
  ) {
    _containers[container.owner] = container
  }

  fun removeContainerByIdentifier(
    identifier: UUID
  ) {
    _containers.remove(identifier)
  }
}