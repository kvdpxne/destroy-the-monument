package me.kvdpxne.dtm.statistics

/**
 * Interface representing the statistical data of a player, including metrics
 * like kills, assists, deaths, and destroyed monuments. This data can be
 * modified and reset, and provides a measurable overview of player performance.
 *
 * This interface extends `Measurable`, allowing for an aggregate measurement
 * that represents the player's overall performance or ranking.
 *
 * @since 0.1.0
 */
interface Statistics : Measurable {

  /**
   * The number of kills recorded by the player.
   *
   * @since 0.1.0
   */
  val kills: Int

  /**
   * The number of assists recorded by the player.
   *
   * @since 0.1.0
   */
  val assists: Int

  /**
   * The number of deaths recorded by the player.
   *
   * @since 0.1.0
   */
  val deaths: Int

  /**
   * The count of monuments destroyed by the player.
   *
   * @since 0.1.0
   */
  val destroyedMonuments: Int

  /**
   * The player's Kill/Death Ratio (KDR), calculated as kills divided by deaths.
   *
   * If the player has zero deaths, the KDR may be calculated differently to
   * avoid division by zero.
   *
   * @since 0.1.0
   */
  val kdr: Float

  /**
   * Adds a specified number of kills to the player's statistics.
   *
   * @param kills The number of kills to add, default is 1.
   * @since 0.1.0
   */
  fun addKills(kills: Int = 1)

  /**
   * Adds a specified number of deaths to the player's statistics.
   *
   * @param deaths The number of deaths to add, default is 1.
   * @since 0.1.0
   */
  fun addDeaths(deaths: Int = 1)

  /**
   * Adds a specified number of assists to the player's statistics.
   *
   * @param assists The number of assists to add, default is 1.
   * @since 0.1.0
   */
  fun addAssists(assists: Int = 1)

  /**
   * Adds a specified number of destroyed monuments to the player's statistics.
   *
   * @param destroyedMonuments The number of monuments to add, default is 1.
   * @since 0.1.0
   */
  fun addDestroyedMonuments(destroyedMonuments: Int = 1)

  /**
   * Subtracts a specified number of kills from the player's statistics.
   *
   * @param kills The number of kills to subtract, default is 1.
   * @since 0.1.0
   */
  fun subtractKills(kills: Int = 1)

  /**
   * Subtracts a specified number of deaths from the player's statistics.
   *
   * @param deaths The number of deaths to subtract, default is 1.
   * @since 0.1.0
   */
  fun subtractDeaths(deaths: Int = 1)

  /**
   * Subtracts a specified number of assists from the player's statistics.
   *
   * @param assists The number of assists to subtract, default is 1.
   * @since 0.1.0
   */
  fun subtractAssists(assists: Int = 1)

  /**
   * Subtracts a specified number of destroyed monuments from the player's
   * statistics.
   *
   * @param destroyedMonuments The number of monuments to subtract, default
   *                           is 1.
   * @since 0.1.0
   */
  fun subtractDestroyedMonuments(destroyedMonuments: Int = 1)

  /**
   * Resets all statistics, including kills, assists, deaths, and destroyed
   * monuments, to their initial values.
   *
   * @since 0.1.0
   */
  fun reset()
}