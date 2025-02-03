package me.kvdpxne.dtm.user.statistics

import java.util.UUID
import me.kvdpxne.dtm.statistics.BasicIdentifiableStatistics

class BasicUserStatistics(
  // @formatter:off
  initialKills             : Int     = 0,
  initialAssists           : Int     = 0,
  initialDeaths            : Int     = 0,
  initialDestroyedMonuments: Int     = 0,
  initialPlayedGames       : Int     = 0,
  initialGamesWon          : Int     = 0,
  initialGamesLost         : Int     = 0,
  initialModified          : Boolean = true,
  identifier               : UUID    = UUID.randomUUID()
  // @formatter:on
) :
  BasicIdentifiableStatistics<UUID>(
    initialKills,
    initialAssists,
    initialDeaths,
    initialDestroyedMonuments,
    initialModified,
    identifier
  ),
  UserStatistics {

  init {

  }

  /**
   * @since 0.1.0
   */
  private var playedGames: Int = initialPlayedGames

  /**
   * @since 0.1.0
   */
  private var gamesWon: Int = initialGamesWon

  /**
   * @since 0.1.0
   */
  private var gamesLost: Int = initialGamesLost

  companion object {

    /**
     * @since 0.1.0
     */
    @Suppress("ConstPropertyName")
    private const val serialVersionUID: Long = -4850424043814475591L
  }

  @Synchronized
  override fun getPlayedGames(): Int {
    return this.playedGames
  }

  override fun setPlayedGames(value: Int) {
    TODO("Not yet implemented")
  }

  @Synchronized
  override fun getGamesWon(): Int {
    return this.gamesWon
  }

  override fun setGamesWon(value: Int) {
    TODO("Not yet implemented")
  }

  @Synchronized
  override fun getGamesLost(): Int {
    return this.gamesLost
  }

  override fun setGamesLost(value: Int) {
    TODO("Not yet implemented")
  }

  override fun addPlayedGames(value: Int) {
    TODO("Not yet implemented")
  }

  override fun addGamesWon(value: Int) {
    TODO("Not yet implemented")
  }

  override fun addGamesLost(value: Int) {
    TODO("Not yet implemented")
  }

  override fun subtractPlayedGames(value: Int) {
    TODO("Not yet implemented")
  }

  override fun subtractGamesWon(value: Int) {
    TODO("Not yet implemented")
  }

  override fun subtractGamesLost(value: Int) {
    TODO("Not yet implemented")
  }

  /**
   * Safely increments an integer value, handling potential overflow.
   *
   * This function attempts to increment the provided integer by 1. If the
   * operation results in an overflow, the maximum possible integer value is
   * returned instead of throwing an exception. After a successful increment,
   * the object on which this function is called is marked as modified.
   *
   * @param value The integer value to increment.
   * @return The incremented value, or the maximum possible integer value if an
   * overflow occurs.
   * @since 0.1.0
   */
  private fun safeIncrement(
    value: Int
  ): Int {
    return try {
      val result: Int = Math.incrementExact(value)
      this.markAsModified()
      result
    } catch (_: ArithmeticException) {
      Int.MAX_VALUE
    }
  }

  @Synchronized
  override fun incrementKills() {
    this.kills = this.safeIncrement(this.kills)
  }

  @Synchronized
  override fun incrementAssists() {
    this.assists = this.safeIncrement(this.assists)
  }

  @Synchronized
  override fun incrementDeaths() {
    this.deaths = this.safeIncrement(this.deaths)
  }

  @Synchronized
  override fun incrementDestroyedMonuments() {
    this.destroyedMonuments = this.safeIncrement(this.destroyedMonuments)
  }

  @Synchronized
  override fun incrementPlayedGames() {
    this.playedGames = this.safeIncrement(this.playedGames)
  }

  @Synchronized
  override fun incrementGamesWon() {
    this.gamesWon = this.safeIncrement(this.gamesWon)
  }

  @Synchronized
  override fun incrementGamesLost() {
    this.gamesLost = this.safeIncrement(this.gamesLost)
  }
}