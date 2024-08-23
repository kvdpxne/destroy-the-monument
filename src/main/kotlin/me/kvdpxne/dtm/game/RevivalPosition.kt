package me.kvdpxne.dtm.game

import me.kvdpxne.dtm.shared.ancillary.Identifiable
import me.kvdpxne.dtm.shared.basics.position.EntityPosition

/**
 * @since 0.1.0
 */
interface RevivalPosition<T : Team> : Identifiable<String>, EntityPosition,
  Teamable<T>