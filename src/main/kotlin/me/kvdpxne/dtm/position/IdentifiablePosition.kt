package me.kvdpxne.dtm.position

import java.util.UUID
import me.kvdpxne.dtm.shared.Identifiable

/**
 * @since 0.1.0
 */
interface IdentifiablePosition<T : Number> : Identifiable<UUID>, Position<T>