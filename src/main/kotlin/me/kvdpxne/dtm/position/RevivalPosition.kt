package me.kvdpxne.dtm.position

import me.kvdpxne.dtm.shared.Identifiable
import me.kvdpxne.dtm.shared.RevivalPositionUuid
import me.kvdpxne.dtm.team.Team
import me.kvdpxne.dtm.team.Teamable

/**
 * @since 0.1.0
 */
interface RevivalPosition<T : Team> : Identifiable<RevivalPositionUuid>, EntityPosition,
  Teamable<T>, Cloneable