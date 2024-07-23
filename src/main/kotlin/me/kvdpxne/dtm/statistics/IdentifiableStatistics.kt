package me.kvdpxne.dtm.statistics

import me.kvdpxne.dtm.shared.ancillary.Identifiable

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
interface IdentifiableStatistics : Statistics, Identifiable<String> {

  /**
   * The unique string identifier associated with this player statistic.
   *
   * @since 0.1.0
   */
  override val identifier: String
}