package me.kvdpxne.dtm.statistics

/**
 * A concrete base class for representing basic player statistics.
 *
 * This class extends `AbstractStatistics` and provides basic player statistics
 * like kills, assists, deaths, and destroyed monuments. It also defines methods
 * for incrementing each statistic value.
 *
 * Subclasses of `BaseStatistics` can potentially add more specific statistics
 * or override the `measure` method to implement a different calculation for
 * the overall player performance summary.
 *
 * @param kills The initial number of kills (default 0).
 * @param assists The initial number of assists (default 0).
 * @param deaths The initial number of deaths (default 0).
 * @param destroyedMonuments The initial number of destroyed monuments (default 0).
 *
 * @since 0.1.0
 */
open class StatisticsImpl(
  // @formatter:off
  override var kills             : Int = 0,
  override var assists           : Int = 0,
  override var deaths            : Int = 0,
  override var destroyedMonuments: Int = 0
  // @formatter:on
) : AbstractStatistics() {

  /**
   * @since 0.1.0
   */
  override val kdr: Float
    get() = String.format("%.3f", (this.kills + this.assists) / this.deaths).toFloat()

  /**
   * Increments the number of kills by 1 (or a specified value).
   *
   * @param kills The number of kills to add (defaults to 1)
   *
   * @since 0.1.0
   */
  override fun addKills(kills: Int) {
    this.kills = this.add(this.kills, kills)
  }

  /**
   * Increments the number of assists by 1 (or a specified value).
   *
   * @param assists The number of assists to add (defaults to 1)
   *
   * @since 0.1.0
   */
  override fun addAssists(assists: Int) {
    this.assists = this.add(this.assists, assists)
  }

  /**
   * Increments the number of deaths by 1 (or a specified value).
   *
   * @param deaths The number of deaths to add (defaults to 1)
   *
   * @since 0.1.0
   */
  override fun addDeaths(deaths: Int) {
    this.deaths = this.add(this.deaths, deaths)
  }

  /**
   * Increments the number of destroyed monuments by 1 (or a specified value).
   *
   * @param destroyedMonuments The number of destroyed monuments to add (defaults to 1)
   *
   * @since 0.1.0
   */
  override fun addDestroyedMonuments(destroyedMonuments: Int) {
    this.destroyedMonuments = this.add(this.destroyedMonuments, destroyedMonuments)
  }

  override fun subtractKills(kills: Int) {
    this.kills = this.subtract(this.kills, kills)
  }

  override fun subtractDeaths(deaths: Int) {
    this.deaths = this.subtract(this.deaths, deaths)
  }

  override fun subtractAssists(assists: Int) {
    this.assists = this.subtract(this.assists, assists)
  }

  override fun subtractDestroyedMonuments(destroyedMonuments: Int) {
    this.destroyedMonuments = this.subtract(this.destroyedMonuments, destroyedMonuments)
  }

  override fun reset() {
    this.kills = 0
    this.assists = 0
    this.deaths = 0
    this.destroyedMonuments = 0
  }

  /**
   * Calculates a basic player performance score based on kills, assists,
   * deaths, and destroyed monuments.
   *
   * This implementation assigns a weight of 1 to kills, 0.5 to assists, 1.4 to
   * destroyed monuments, and subtracts 0.8 for each death.
   *
   * Subclasses can override this method to implement a different calculation
   * for the overall performance summary.
   *
   * @return A float value representing the calculated player performance score.
   *
   * @since 0.1.0
   */
  override fun measure(): Float {
    var value = 0F

    value += this.kills * 1F
    value += this.assists * 0.5F
    value += this.destroyedMonuments * 1.4F
    value -= this.deaths * 0.8F

    return value
  }
}