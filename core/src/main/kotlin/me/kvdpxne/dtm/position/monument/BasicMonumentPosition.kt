package me.kvdpxne.dtm.position.monument

import java.util.UUID
import me.kvdpxne.dtm.capabilities.Identifiable
import me.kvdpxne.dtm.InternalIdentifiable
import me.kvdpxne.dtm.position.BasicBlockPosition
import me.kvdpxne.dtm.team.Team

class BasicMonumentPosition(
  // @formatter:off
              x         : Int,
              y         : Int,
              z         : Int,
  private val team      : Team,
              identifier: UUID = UUID.randomUUID()
  // @formatter:on
) :
  BasicBlockPosition(
    x,
    y,
    z
  ),
  MonumentPosition {

  companion object {

    /**
     * @since 0.1.0
     */
    @Suppress("ConstPropertyName")
    private const val serialVersionUID: Long = -3417808231445169024L
  }

  /**
   * @since 0.1.0
   */
  private val identifiable: Identifiable<UUID> by lazy {
    InternalIdentifiable(identifier)
  }

  /**
   * @since 0.1.0
   */
  @Volatile
  private var destroyed: Boolean = false

  override fun getIdentifier(): UUID {
    return this.identifiable.getIdentifier()
  }

  override fun getTeam(): Team {
    return this.team
  }

  override fun isDestroyed(): Boolean {
    return this.destroyed
  }

  override fun setDestroyed(destroyed: Boolean) {
    this.destroyed = destroyed
  }

  override fun markAsDestroyed() {
    this.destroyed = true
  }

  override fun unmarkAsDestroyed() {
    this.destroyed = false
  }
}