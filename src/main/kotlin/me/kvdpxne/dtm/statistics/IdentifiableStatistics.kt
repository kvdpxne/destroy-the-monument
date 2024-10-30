package me.kvdpxne.dtm.statistics

import java.util.UUID
import me.kvdpxne.dtm.shared.Identifiable

/**
 * Defines a contract for player statistics that can be measured, compared,
 * and uniquely identified.
 *
 * This interface extends both `Statistics` and `Identifiable`. Statistics
 * objects can provide a measurable value (likely representing an overall
 * summary of the player's performance) and have a unique string identifier
 * for easy retrieval.
 *
 * @since 0.1.0
 */
interface IdentifiableStatistics : Identifiable<UUID>, Statistics