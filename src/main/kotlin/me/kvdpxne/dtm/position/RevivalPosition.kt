package me.kvdpxne.dtm.position

import java.util.UUID
import me.kvdpxne.dtm.shared.Identifiable
import me.kvdpxne.dtm.team.Team
import me.kvdpxne.dtm.team.Teamable

/**
 * @since 0.1.0
 */
interface RevivalPosition<T : Team> : Identifiable<UUID>, EntityPosition,
  Teamable<T>, Cloneable