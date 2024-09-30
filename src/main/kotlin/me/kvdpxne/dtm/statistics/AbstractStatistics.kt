package me.kvdpxne.dtm.statistics

/**
 * An abstract base class for representing player statistics.
 *
 * This class provides helper methods for adding and subtracting values to
 * statistics while ensuring the values don't go below zero. It inherits from
 * the `Statistics` interface, indicating that subclasses can be measured
 * and compared.
 *
 * Subclasses of `AbstractStatistics` should implement the `measure` method to
 * define the specific logic for calculating the overall player performance
 * summary.
 *
 * @since 0.1.0
 */
abstract class AbstractStatistics protected constructor() : Statistics {

  /**
   * Ensures the sum of a statistic (kills, deaths, etc.), and the provided
   * value doesn't fall below zero.
   *
   * This protected helper function is used by subclasses to safely add values
   * to statistics. It checks for zero-valued additions and prevents the
   * statistic from becoming negative.
   *
   * @param current The current value of the statistic (e.g., number of kills).
   * @param value The value to be added to the statistic.
   * @return The new value after adding, capped at 0.
   *
   * @since 0.1.0
   */
  protected fun add(
    current: Int,
    value: Int
  ): Int {
    if (0 == value) {
      return current
    }
    val newValue = current + value
    if (0 > newValue) {
      return 0
    }
    return newValue
  }

  /**
   * Ensures the difference of a statistic (kills, deaths, etc.), and the
   * provided value doesn't fall below zero.
   *
   * This protected helper function is used by subclasses to safely subtract
   * values from statistics. It checks for zero-valued subtractions and
   * prevents the statistic from becoming negative.
   *
   * @param current The current value of the statistic (e.g., number of kills).
   * @param value The value to be subtracted from the statistic.
   * @return The new value after subtracting, capped at 0.
   *
   * @since 0.1.0
   */
  protected fun subtract(
    current: Int,
    value: Int
  ): Int {
    if (0 == value) {
      return current
    }
    val newValue = current - value
    if (0 > newValue) {
      return 0
    }
    return newValue
  }

  /**
   * Abstract method that subclasses must implement to define the logic for
   * calculating the overall player performance summary.
   *
   * The specific implementation of this method depends on the type of
   * statistics being tracked.
   *
   * @return A float value representing the overall player performance summary.
   *
   * @since 0.1.0
   */
  abstract override fun measure(): Float
}