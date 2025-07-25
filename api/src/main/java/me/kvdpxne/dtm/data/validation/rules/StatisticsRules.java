package me.kvdpxne.dtm.data.validation.rules;

import me.kvdpxne.dtm.UnsubstantiatedInitializationError;

/**
 * Defines validation boundaries for statistical values within the application.
 * Provides immutable constants representing the minimum and maximum allowable
 * values for statistical measurements.
 * <p>
 * This class serves as a constants container and cannot be instantiated.
 *
 * @author Łukasz Pietrzak (kvdpxne)
 * @version 0.1.0
 * @since 0.1.0
 */
public final class StatisticsRules {

  /**
   * The absolute minimum value allowed for statistical measurements.
   * <p>
   * Represents the inclusive lower boundary ({@code 0}).
   *
   * @since 0.1.0
   */
  public static final int MIN_VALUE = 0;


  /**
   * The absolute maximum value allowed for statistical measurements.
   * Equals {@link Integer#MAX_VALUE} (2<sup>31</sup>-1 = 2,147,483,647),
   * representing the inclusive upper boundary for integer-based statistics.
   *
   * @since 0.1.0
   */
  public static final int MAX_VALUE = Integer.MAX_VALUE;

  /**
   * Private constructor to prevent instantiation.
   * <p>
   * Throws {@link UnsubstantiatedInitializationError} if invoked,
   * enforcing this class's nature as a static utility container.
   *
   * @throws UnsubstantiatedInitializationError Always thrown upon invocation
   * @since 0.1.0
   */
  private StatisticsRules() {
    throw new UnsubstantiatedInitializationError(this.getClass());
  }
}
