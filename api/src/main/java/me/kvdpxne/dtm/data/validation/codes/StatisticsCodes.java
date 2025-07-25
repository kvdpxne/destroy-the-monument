package me.kvdpxne.dtm.data.validation.codes;

import me.kvdpxne.dtm.UnsubstantiatedInitializationError;

/**
 * Defines error codes specific to statistics validation failures within the application.
 * These unique numeric identifiers categorize different types of statistical data validation errors,
 * enabling precise error handling and diagnostic communication.
 * <p>
 * This class serves as a constants container and cannot be instantiated.
 *
 * @author Łukasz Pietrzak (kvdpxne)
 * @version 0.1.0
 * @since 0.1.0
 */
public final class StatisticsCodes {

  /**
   * Error code indicating an invalid kills statistic value.
   * Signifies that a kills value failed validation against established statistical rules.
   * <p>
   * Value: {@value #INVALID_KILLS} (-1,310,508,041)
   *
   * @since 0.1.0
   */
  public static final int INVALID_KILLS = 0xb1e33bf7;

  /**
   * Error code indicating an invalid assists statistic value.
   * Signifies that an assists value failed validation against established statistical rules.
   * <p>
   * Value: {@value #INVALID_ASSISTS} (-1,435,312,135)
   *
   * @since 0.1.0
   */
  public static final int INVALID_ASSISTS = 0xaa72dff9;

  /**
   * Error code indicating an invalid deaths statistic value.
   * Signifies that a deaths value failed validation against established statistical rules.
   * <p>
   * Value: {@value #INVALID_DEATHS} (-1,861,249,003)
   *
   * @since 0.1.0
   */
  public static final int INVALID_DEATHS = 0x910f9815;

  /**
   * Error code indicating an invalid destroyed monuments statistic value.
   * Signifies that a destroyed monuments value failed validation against established rules.
   * <p>
   * Value: {@value #INVALID_DESTROYED_MONUMENTS} (-607,534,911)
   *
   * @since 0.1.0
   */
  public static final int INVALID_DESTROYED_MONUMENTS = 0xdbc9c0c1;

  /**
   * Error code indicating an invalid played games statistic value.
   * Signifies that a played games value failed validation against established statistical rules.
   * <p>
   * Value: {@value #INVALID_PLAYED_GAMES} (-2,108,348,783)
   *
   * @since 0.1.0
   */
  public static final int INVALID_PLAYED_GAMES = 0x82552691;

  /**
   * Error code indicating an invalid games won statistic value.
   * Signifies that a games won value failed validation against established statistical rules.
   * <p>
   * Value: {@value #INVALID_GAMES_WON} (-1,925,573,289)
   *
   * @since 0.1.0
   */
  public static final int INVALID_GAMES_WON = 0x8d3a1557;

  /**
   * Error code indicating an invalid games lost statistic value.
   * Signifies that a games lost value failed validation against established statistical rules.
   * <p>
   * Value: {@value #INVALID_GAMES_LOST} (-2,103,650,064)
   *
   * @since 0.1.0
   */
  public static final int INVALID_GAMES_LOST = 0x829cd8f0;

  /**
   * Private constructor to prevent instantiation.
   * <p>
   * Throws {@link UnsubstantiatedInitializationError} if invoked,
   * enforcing this class's nature as a static utility container.
   *
   * @throws UnsubstantiatedInitializationError Always thrown upon invocation
   * @since 0.1.0
   */
  private StatisticsCodes() {
    throw new UnsubstantiatedInitializationError(this.getClass());
  }
}
