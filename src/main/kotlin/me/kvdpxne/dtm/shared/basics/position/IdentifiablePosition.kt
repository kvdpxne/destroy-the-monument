package me.kvdpxne.dtm.shared.basics.position

import me.kvdpxne.dtm.shared.ancillary.Identifiable

/**
 * @since 0.1.0
 */
interface IdentifiablePosition<T : Number> : Identifiable<String>, Position<T>