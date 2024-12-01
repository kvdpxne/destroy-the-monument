package me.kvdpxne.dtm.container

import me.kvdpxne.dtm.shared.PacketHandler

/**
 * Represents an entity capable of opening containers in the plugin.
 *
 * Implementations of this interface define the logic or behavior for opening
 * specific container types.
 *
 * @since 0.1.0
 */
interface ContainerOpener<T> : PacketHandler<T>