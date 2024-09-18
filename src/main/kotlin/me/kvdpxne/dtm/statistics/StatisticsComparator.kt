package me.kvdpxne.dtm.statistics

import me.kvdpxne.dtm.user.UserStatistics

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
    type: Int = StatisticsCriteria.BY_KILLS
  ): Comparator<Statistics> {
    require(type in 0..6) {
      "Unsupported comparison criteria: $type"
    }

    return Comparator { a: Statistics, b: Statistics ->

      // Handle UserStatistics objects first
      if (a is UserStatistics && b is UserStatistics) {
        return@Comparator when (type) {
          0 -> b.kills - a.kills
          1 -> b.deaths - a.deaths
          2 -> b.assists - a.assists
          3 -> b.playedGames - a.playedGames
          4 -> b.gamesWon - a.gamesWon
          5 -> b.gamesLost - a.gamesLost
          6 -> b.destroyedMonuments - a.destroyedMonuments
          else -> throw IllegalArgumentException("Unknown type $type")
        }
      }

      // Then handle BaseStatistics objects
      if (a is StatisticsImpl && b is StatisticsImpl) {
        return@Comparator when (type) {
          0 -> b.kills - a.kills
          1 -> b.deaths - a.deaths
          2 -> b.assists - a.assists
          6 -> b.destroyedMonuments - a.destroyedMonuments
          else -> throw IllegalArgumentException("Unknown type $type")
        }
      }

      throw StatisticsException(
        "Unsupported statistics object types for comparison."
      )
    }
  }
}