package me.kvdpxne.dtm.statistics

import java.io.Serializable
import me.kvdpxne.dtm.Identifiable
import me.kvdpxne.dtm.InternalIdentifiable

open class BasicIdentifiableStatistics<T : Serializable>(
  // @formatter:off
  initialKills             : Int = 0,
  initialAssists           : Int = 0,
  initialDeaths            : Int = 0,
  initialDestroyedMonuments: Int = 0,
  initialModified          : Boolean = true,
  identifier               : T
  // @formatter:on
) :
  BasicStatistics(
    initialKills,
    initialAssists,
    initialDeaths,
    initialDestroyedMonuments,
    initialModified
  ),
  IdentifiableStatistics<T> {

  /**
   * @since 0.1.0
   */
  private val identifiable: Identifiable<T> by lazy {
    InternalIdentifiable(identifier)
  }

  companion object {

    /**
     * @since 0.1.0
     */
    @Suppress("ConstPropertyName")
    private const val serialVersionUID: Long = 3823244154504522077L
  }

  override fun getIdentifier(): T {
    return this.identifiable.identifier
  }
}