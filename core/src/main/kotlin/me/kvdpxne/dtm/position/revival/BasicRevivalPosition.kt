package me.kvdpxne.dtm.position.revival

import java.util.UUID
import me.kvdpxne.dtm.Identifiable
import me.kvdpxne.dtm.InternalIdentifiable
import me.kvdpxne.dtm.position.BasicEntityPosition
import me.kvdpxne.dtm.team.Team

class BasicRevivalPosition(
  // @formatter:off
              x         : Double,
              y         : Double,
              z         : Double,
              pitch     : Float,
              yaw       : Float,
  private val team      : Team,
              identifier: UUID = UUID.randomUUID()
  // @formatter:on
) :
  BasicEntityPosition(
    x,
    y,
    z,
    pitch,
    yaw
  ),
  RevivalPosition {

  companion object {

    /**
     * @since 0.1.0
     */
    @Suppress("ConstPropertyName")
    private const val serialVersionUID: Long = -618492211318755554L
  }

  /**
   * @since 0.1.0
   */
  private val identifiable: Identifiable<UUID> by lazy {
    InternalIdentifiable(identifier)
  }

  override fun getIdentifier(): UUID {
    return this.identifiable.identifier
  }

  override fun getTeam(): Team {
    return this.team
  }
}