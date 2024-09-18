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

  val kills: Int

  val assists: Int

  val deaths: Int

  val destroyedMonuments: Int

  fun addKills(kills: Int = 1)

  fun addDeaths(deaths: Int = 1)

  fun addAssists(assists: Int = 1)

  fun addDestroyedMonuments(destroyedMonuments: Int = 1)


}