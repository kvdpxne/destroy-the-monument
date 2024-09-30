package me.kvdpxne.dtm.statistics

/**
 * Interface representing player statistics that can be measured and compared.
 *
 * This interface inherits from `Measurable`, indicating that statistics
 * objects can provide a measurable value.
 * The specific value returned by `measure` likely represents some overall
 * summary of the player's performance.
 *
 * @since 0.1.0
 */
interface Statistics : Measurable {

  /**
   * @since 0.1.0
   */
  val kills: Int

  /**
   * @since 0.1.0
   */
  val assists: Int

  /**
   * @since 0.1.0
   */
  val deaths: Int

  /**
   * @since 0.1.0
   */
  val destroyedMonuments: Int

  /**
   * @since 0.1.0
   */
  val kdr: Float

  /**
   * @since 0.1.0
   */
  fun addKills(kills: Int = 1)

  /**
   * @since 0.1.0
   */
  fun addDeaths(deaths: Int = 1)

  /**
   * @since 0.1.0
   */
  fun addAssists(assists: Int = 1)

  /**
   * @since 0.1.0
   */
  fun addDestroyedMonuments(destroyedMonuments: Int = 1)

  /**
   * @since 0.1.0
   */
  fun subtractKills(kills: Int = 1)

  /**
   * @since 0.1.0
   */
  fun subtractDeaths(deaths: Int = 1)

  /**
   * @since 0.1.0
   */
  fun subtractAssists(assists: Int = 1)

  /**
   * @since 0.1.0
   */
  fun subtractDestroyedMonuments(destroyedMonuments: Int = 1)

  /**
   * @since 0.1.0
   */
  fun reset()
}