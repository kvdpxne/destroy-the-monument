package me.kvdpxne.dtm.game

import java.util.UUID
import me.kvdpxne.dtm.shared.ancillary.Identifiable
import me.kvdpxne.dtm.shared.basics.position.EntityPosition

/**
 * @since 0.1.0
 */
interface RevivalPosition<T : Team> : Identifiable<UUID>, EntityPosition,
  Teamable<T>, Cloneable