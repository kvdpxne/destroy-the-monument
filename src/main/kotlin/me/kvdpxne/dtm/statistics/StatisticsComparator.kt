package me.kvdpxne.dtm.statistics

import kotlin.math.roundToInt
import me.kvdpxne.dtm.statistics.StatisticsCriteria.BY_ASSISTS
import me.kvdpxne.dtm.statistics.StatisticsCriteria.BY_DEATHS
import me.kvdpxne.dtm.statistics.StatisticsCriteria.BY_DESTROYED_MONUMENTS
import me.kvdpxne.dtm.statistics.StatisticsCriteria.BY_KDA
import me.kvdpxne.dtm.statistics.StatisticsCriteria.BY_KILLS

/**
 * Provides utility functions for creating comparators used to sort statistics
 * objects.
 *
 * This class offers a static method, `compare`, for generating comparators
 * that sort statistics objects based on a specific criterion.
 *
 * @since 0.1.0
 */
object StatisticsComparator {

  /**
   * Creates a comparator for comparing statistics objects based on a specific
   * type.
   *
   * This function takes an integer `type` parameter that determines which
   * statistic to compare. It returns a `Comparator<T>` where `T` is any type
   * that extends the `Statistics` interface.
   *
   * The supported types are:
   *  * 0: Kills (higher value sorts higher)
   *  * 1: Deaths (lower value sorts higher)
   *  * 2: Assists (higher value sorts higher)
   *  * 3: Played Games (higher value sorts higher)
   *  * 4: Games Won (higher value sorts higher)
   *  * 5: Games Lost (lower value sorts higher)
   *  * 6: Destroyed Monuments (higher value sorts higher)
   *
   * Any other value for `type` will throw an `IllegalArgumentException`.
   *
   * @param type The integer representing the statistic to compare.
   * @throws IllegalArgumentException if the provided `type` is not supported.
   * @since 0.1.0
   */
  fun compare(
    type: Int = BY_KILLS
  ): Comparator<Statistics> {
    return Comparator { a: Statistics, b: Statistics ->
      when (type) {
        BY_KILLS -> b.kills - a.kills
        BY_DEATHS -> b.deaths - a.deaths
        BY_ASSISTS -> b.assists - a.assists
        BY_KDA -> (b.kdr - a.kdr).roundToInt()
        BY_DESTROYED_MONUMENTS -> b.destroyedMonuments - a.destroyedMonuments
        else -> throw IllegalArgumentException("Unknown type $type")
      }
    }
  }
}