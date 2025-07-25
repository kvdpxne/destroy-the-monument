package me.kvdpxne.dtm.statistics

import me.kvdpxne.dtm.shared.language.toSingleLines
import me.kvdpxne.dtm.capabilities.state.MutableState

/**
 * @since 0.1.0
 */
open class BasicStatistics(
  // @formatter:off
  initialKills             : Int = 0,
  initialAssists           : Int = 0,
  initialDeaths            : Int = 0,
  initialDestroyedMonuments: Int = 0,
  initialModified          : Boolean = true
  // @formatter:on
) :
  IncompleteStatistics(
    initialModified
  ),
  Statistics,
  MutableState {

  init {
    require(0 <= initialKills) {
      """
        The passed number of kills (currently "$initialKills") must be greater
        than or equal to 0.
      """.toSingleLines()
    }

    require(0 <= initialAssists) {
      """
        The passed number of assists (currently "$initialAssists") must be
        greater than or equal to 0.
      """.toSingleLines()
    }

    require(0 <= initialDeaths) {
      """
        The passed number of deaths (currently "$initialDeaths") must be
        greater than or equal to 0.
      """.toSingleLines()
    }

    require(0 <= initialDestroyedMonuments) {
      """
        The passed number of destroyed monuments (currently
        "$initialDestroyedMonuments") must be greater than or equal to 0.
      """.toSingleLines()
    }
  }

  /**
   * @since 0.1.0
   */
  @JvmField
  protected var kills: Int = initialKills

  /**
   * @since 0.1.0
   */
  @JvmField
  protected var assists: Int = initialAssists

  /**
   * @since 0.1.0
   */
  @JvmField
  protected var deaths: Int = initialDeaths

  /**
   * @since 0.1.0
   */
  @JvmField
  protected var destroyedMonuments: Int = initialDestroyedMonuments

  companion object {

    /**
     * @since 0.1.0
     */
    @Suppress("ConstPropertyName")
    private const val serialVersionUID: Long = 4004546673656930486L
  }

  @Synchronized
  override fun getKills(): Int {
    return this.kills
  }

  override fun setKills(
    value: Int
  ) {
    require(0 <= value) {
      """
        The passed number of kills "$value" to be set must be greater than
        or equal to 0.
      """.toSingleLines()
    }

    synchronized(this) {
      if (value == this.kills) {
        return
      }

      this.kills = value
      this.markAsModified()
    }
  }

  @Synchronized
  override fun getAssists(): Int {
    return this.assists
  }

  override fun setAssists(
    value: Int
  ) {
    require(0 <= value) {
      """
        The passed number of assists "$value" to be set must be greater than
        or equal to 0.
      """.toSingleLines()
    }

    synchronized(this) {
      if (value == this.assists) {
        return
      }

      this.assists = value
      this.markAsModified()
    }
  }

  @Synchronized
  override fun getDeaths(): Int {
    return this.deaths
  }

  override fun setDeaths(
    value: Int
  ) {
    require(0 <= value) {
      """
        The passed number of deaths "$value" to be set must be greater than
        or equal to 0.
      """.toSingleLines()
    }

    synchronized(this) {
      if (value == this.deaths) {
        return
      }

      this.deaths = value
      this.markAsModified()
    }
  }

  @Synchronized
  override fun getDestroyedMonuments(): Int {
    return this.destroyedMonuments
  }

  override fun setDestroyedMonuments(
    value: Int
  ) {
    require(0 <= value) {
      """
        The passed number of destroyed monuments "$value" to be set must be
        greater than or equal to 0.
      """.toSingleLines()
    }

    synchronized(this) {
      if (value == this.destroyedMonuments) {
        return
      }

      this.destroyedMonuments = value
      this.markAsModified()
    }
  }

  @Synchronized
  override fun calculateKdRatio(): Float {
    if (0 != this.deaths) {
      return (this.kills / this.deaths).toFloat()
    }

    return this.kills.toFloat()
  }

  @Synchronized
  override fun calculateKdaRatio(): Float {
    if (0 != this.deaths) {
      return ((this.kills + this.assists / 2) / this.deaths).toFloat()
    }

    return (this.kills + this.assists / 2).toFloat()
  }

  override fun addKills(
    value: Int
  ) {
    require(0 < value) {
      """
        The passed number of kills "$value" to add must be greater than 0.
      """.toSingleLines()
    }

    synchronized(this) {
      this.kills = this.add(this.kills, value)
    }
  }

  override fun addAssists(
    value: Int
  ) {
    require(0 < value) {
      """
        The passed number of assists "$value" to add must be greater than 0.
      """.toSingleLines()
    }

    synchronized(this) {
      this.assists = this.add(this.assists, value)
    }
  }

  override fun addDeaths(
    value: Int
  ) {
    require(0 < value) {
      """
        The passed number of deaths "$value" to add must be greater than 0.
      """.toSingleLines()
    }

    synchronized(this) {
      this.deaths = this.add(this.deaths, value)
    }
  }

  override fun addDestroyedMonuments(
    value: Int
  ) {
    require(0 < value) {
      """
        The passed number of destroyed monuments "$value" to add must be
        greater than 0.
      """.toSingleLines()
    }

    synchronized(this) {
      this.destroyedMonuments = this.add(this.destroyedMonuments, value)
    }
  }

  override fun subtractKills(
    value: Int
  ) {
    require(0 < value) {
      """
        The passed number of kills "$value" to subtract must be greater
        than 0.
      """.toSingleLines()
    }

    synchronized(this) {
      this.kills = this.subtract(this.kills, value)
    }
  }

  override fun subtractAssists(
    value: Int
  ) {
    require(0 < value) {
      """
        The passed number of assists "$value" to subtract must be greater
        than 0.
      """.toSingleLines()
    }

    synchronized(this) {
      this.assists = this.subtract(this.assists, value)
    }
  }

  override fun subtractDeaths(
    value: Int
  ) {
    require(0 < value) {
      """
        The passed number of deaths "$value" to subtract must be greater
        than 0.
      """.toSingleLines()
    }

    synchronized(this) {
      this.deaths = this.subtract(this.deaths, value)
    }
  }

  override fun subtractDestroyedMonuments(
    value: Int
  ) {
    require(0 < value) {
      """
        The passed number of destroyed monuments "$value" to subtract must be
        greater than 0.
      """.toSingleLines()
    }

    synchronized(this) {
      this.destroyedMonuments= this.subtract(this.destroyedMonuments, value)
    }
  }

  @Synchronized
  override fun incrementKills() {
    this.kills = Math.incrementExact(this.kills)
    this.markAsModified()
  }

  @Synchronized
  override fun incrementAssists() {
    this.assists = Math.incrementExact(this.assists)
    this.markAsModified()
  }

  @Synchronized
  override fun incrementDeaths() {
    this.deaths = Math.incrementExact(this.deaths)
    this.markAsModified()
  }

  @Synchronized
  override fun incrementDestroyedMonuments() {
    this.destroyedMonuments = Math.incrementExact(this.destroyedMonuments)
    this.markAsModified()
  }

  @Synchronized
  override fun copy(): Statistics {
    return BasicStatistics(
      this.kills,
      this.assists,
      this.deaths,
      this.destroyedMonuments,
      this.wasModified()
    )
  }

  @Synchronized
  override fun reset() {
    this.kills = 0
    this.assists = 0
    this.deaths = 0
    this.destroyedMonuments = 0

    this.markAsModified()
  }
}