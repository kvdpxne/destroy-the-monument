package me.kvdpxne.dtm.shared.basics

import me.kvdpxne.dtm.shared.ancillary.Identifiable

/**
 * @since 0.1.0
 */
interface IdentifiablePosition<T : Number> : Position<T>, Identifiable<String>