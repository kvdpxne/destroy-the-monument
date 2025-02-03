package me.kvdpxne.dtm.statistics;

/**
 * Defines constants representing criteria for comparing statistics objects.
 *
 * @since 0.1.0
 */
public final class StatisticsCriteria {

  /**
   * Constant representing the criteria for comparing by the number of kills.
   *
   * @since 0.1.0
   */
  public static final byte BY_KILLS = 0;

  /**
   * Constant representing the criteria for comparing by the number of assists.
   *
   * @since 0.1.0
   */
  public static final byte BY_ASSISTS = 3;

  /**
   * Constant representing the criteria for comparing by the number of deaths.
   *
   * @since 0.1.0
   */
  public static final byte BY_DEATHS = 1;

  /**
   * Constant representing the criteria for comparing by the number of destroyed
   * monuments.
   *
   * @since 0.1.0
   */
  public static final byte BY_DESTROYED_MONUMENTS = 7;

  /**
   * @since 0.1.0
   */
  public static final byte BY_KD_RATIO = 8;

  /**
   * @since 0.1.0
   */
  public static final byte BY_KDA_RATIO = 9;

  /**
   * Constant representing the criteria for comparing by the number of played
   * games.
   *
   * @since 0.1.0
   */
  public static final byte BY_PLAYED_GAMES = 4;

  /**
   * Constant representing the criteria for comparing by the number of games
   * won.
   *
   * @since 0.1.0
   */
  public static final byte BY_GAMES_WON = 5;

  /**
   * Constant representing the criteria for comparing by the number of games
   * lost.
   *
   * @since 0.1.0
   */
  public static final byte BY_GAMES_LOST = 6;

  /**
   * @since 0.1.0
   */
  private StatisticsCriteria() {
    throw new AssertionError("No " + this.getClass().getName() + " instances for you!");
  }
}
