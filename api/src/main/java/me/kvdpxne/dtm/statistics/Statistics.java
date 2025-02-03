package me.kvdpxne.dtm.statistics;

import java.io.Serializable;
import me.kvdpxne.dtm.Copyable;
import me.kvdpxne.dtm.state.State;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

/**
 * Represents a statistics interface for tracking game-related metrics such as
 * kills, assists, deaths, and destroyed monuments.
 * <p>
 * All methods in this interface are thread-safe and ensure atomic operations
 * for consistent updates in concurrent environments.
 *
 * @since 0.1.0
 */
public interface Statistics
  extends
  Copyable<Statistics>,
  State,
  Serializable {

  /**
   * Retrieves the number of kills.
   *
   * @return the number of kills, guaranteed to be non-negative.
   * @since 0.1.0
   */
  @Range(from = 0, to = Integer.MAX_VALUE)
  int getKills();

  /**
   * Sets the number of kills.
   *
   * @param kills the new number of kills, must be non-negative.
   * @throws IllegalArgumentException if {@code kills} is negative.
   * @since 0.1.0
   */
  void setKills(
    final @Range(from = 0, to = Integer.MAX_VALUE) int kills
  );

  /**
   * Retrieves the number of assists.
   *
   * @return the number of assists, guaranteed to be non-negative.
   * @since 0.1.0
   */
  @Range(from = 0, to = Integer.MAX_VALUE)
  int getAssists();

  /**
   * Sets the number of assists.
   *
   * @param assists the new number of assists, must be non-negative.
   * @throws IllegalArgumentException if {@code assists} is negative.
   * @since 0.1.0
   */
  void setAssists(
    final @Range(from = 0, to = Integer.MAX_VALUE) int assists
  );

  /**
   * Retrieves the number of deaths.
   *
   * @return the number of deaths, guaranteed to be non-negative.
   * @since 0.1.0
   */
  @Range(from = 0, to = Integer.MAX_VALUE)
  int getDeaths();

  /**
   * Sets the number of deaths.
   *
   * @param deaths the new number of deaths, must be non-negative.
   * @throws IllegalArgumentException if {@code deaths} is negative.
   * @since 0.1.0
   */
  void setDeaths(
    final @Range(from = 0, to = Integer.MAX_VALUE) int deaths
  );

  /**
   * Retrieves the number of destroyed monuments.
   *
   * @return the number of destroyed monuments, guaranteed to be non-negative.
   * @since 0.1.0
   */
  @Range(from = 0, to = Integer.MAX_VALUE)
  int getDestroyedMonuments();

  /**
   * Sets the number of destroyed monuments.
   *
   * @param destroyedMonuments the new number of destroyed monuments, must be
   *                           non-negative.
   * @throws IllegalArgumentException if {@code destroyedMonuments} is
   *                                  negative.
   * @since 0.1.0
   */
  void setDestroyedMonuments(
    final @Range(from = 0, to = Integer.MAX_VALUE) int destroyedMonuments
  );

  /**
   * Calculates the kill/death (KD) ratio.
   *
   * @return the KD ratio, guaranteed to be non-negative. If deaths are zero,
   * the result is effectively kills.
   * @since 0.1.0
   */
  @Range(from = 0, to = Long.MAX_VALUE)
  float calculateKdRatio();

  /**
   * Calculates the kill/death/assist (KDA) ratio.
   *
   * @return the KDA ratio, guaranteed to be non-negative. If deaths are zero,
   * the result is effectively (kills + assists).
   * @since 0.1.0
   */
  @Range(from = 0, to = Long.MAX_VALUE)
  float calculateKdaRatio();

  /**
   * Adds a specified number of kills.
   *
   * @param kills the number of kills to add, must be non-negative.
   * @throws IllegalArgumentException if {@code kills} is negative.
   * @throws ArithmeticException      if the operation results in an overflow.
   * @since 0.1.0
   */
  void addKills(
    final @Range(from = 0, to = Integer.MAX_VALUE) int kills
  );

  /**
   * Adds a specified number of assists.
   *
   * @param assists the number of assists to add, must be non-negative.
   * @throws IllegalArgumentException if {@code assists} is negative.
   * @throws ArithmeticException      if the operation results in an overflow.
   * @since 0.1.0
   */
  void addAssists(
    final @Range(from = 0, to = Integer.MAX_VALUE) int assists
  );

  /**
   * Adds a specified number of deaths.
   *
   * @param deaths the number of deaths to add, must be non-negative.
   * @throws IllegalArgumentException if {@code deaths} is negative.
   * @throws ArithmeticException      if the operation results in an overflow.
   * @since 0.1.0
   */
  void addDeaths(
    final @Range(from = 0, to = Integer.MAX_VALUE) int deaths
  );

  /**
   * Adds a specified number of destroyed monuments.
   *
   * @param monuments the number of monuments to add, must be non-negative.
   * @throws IllegalArgumentException if {@code monuments} is negative.
   * @throws ArithmeticException      if the operation results in an overflow.
   * @since 0.1.0
   */
  void addDestroyedMonuments(
    final @Range(from = 0, to = Integer.MAX_VALUE) int monuments
  );

  /**
   * Subtracts a specified number of kills.
   *
   * @param kills the number of kills to subtract, must be non-negative.
   * @throws IllegalArgumentException if {@code kills} is negative or exceeds
   *                                  the current count.
   * @throws ArithmeticException      if the operation results in an underflow.
   * @since 0.1.0
   */
  void subtractKills(
    final @Range(from = 0, to = Integer.MAX_VALUE) int kills
  );

  /**
   * Subtracts a specified number of assists.
   *
   * @param assists the number of assists to subtract, must be non-negative.
   * @throws IllegalArgumentException if {@code assists} is negative or exceeds
   *                                  the current count.
   * @throws ArithmeticException      if the operation results in an underflow.
   * @since 0.1.0
   */
  void subtractAssists(
    final @Range(from = 0, to = Integer.MAX_VALUE) int assists
  );

  /**
   * Subtracts a specified number of deaths.
   *
   * @param deaths the number of deaths to subtract, must be non-negative.
   * @throws IllegalArgumentException if {@code deaths} is negative or exceeds
   *                                  the current count.
   * @throws ArithmeticException      if the operation results in an underflow.
   * @since 0.1.0
   */
  void subtractDeaths(
    final @Range(from = 0, to = Integer.MAX_VALUE) int deaths
  );

  /**
   * Subtracts a specified number of destroyed monuments.
   *
   * @param monuments the number of monuments to subtract, must be
   *                  non-negative.
   * @throws IllegalArgumentException if {@code monuments} is negative or
   *                                  exceeds the current count.
   * @throws ArithmeticException      if the operation results in an underflow.
   * @since 0.1.0
   */
  void subtractDestroyedMonuments(
    final @Range(from = 0, to = Integer.MAX_VALUE) int monuments
  );

  /**
   * Increments the kill count by one.
   *
   * @throws ArithmeticException if the operation results in an overflow.
   * @since 0.1.0
   */
  void incrementKills();

  /**
   * Increments the assist count by one.
   *
   * @throws ArithmeticException if the operation results in an overflow.
   * @since 0.1.0
   */
  void incrementAssists();

  /**
   * Increments the death count by one.
   *
   * @throws ArithmeticException if the operation results in an overflow.
   * @since 0.1.0
   */
  void incrementDeaths();

  /**
   * Increments the destroyed monuments count by one.
   *
   * @throws ArithmeticException if the operation results in an overflow.
   * @since 0.1.0
   */
  void incrementDestroyedMonuments();

  /**
   * Checks if the statistics object has been modified.
   *
   * @return {@code true} if the statistics have been modified, {@code false}
   * otherwise.
   * @since 0.1.0
   */
  @Override
  boolean wasModified();

  /**
   * Creates and returns a deep copy of the current statistics object.
   *
   * @return a new instance of {@link Statistics} with identical values.
   * @since 0.1.0
   */
  @Override
  @NotNull
  Statistics copy();

  /**
   * Resets all statistics to their default values (e.g., zero).
   *
   * @since 0.1.0
   */
  void reset();
}
