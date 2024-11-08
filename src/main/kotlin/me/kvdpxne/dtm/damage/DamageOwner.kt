package me.kvdpxne.dtm.damage

import java.util.UUID
import me.kvdpxne.dtm.shared.debug.Debug

class DamageOwner(
  // @formatter:off
  val owner  : UUID,
  val history: MutableSet<Damage> = mutableSetOf(),
  // @formatter:on
) {

  fun addDamage(
    attacker: UUID,
    damages: Double
  ) {
    if (this.owner == attacker) {
      return
    }

    val damage = Damage(attacker, damages)
    this.history.add(damage)

    Debug.log {
      ""
    }
  }

  /**
   * @since 0.1.0
   */
  fun removeDamages() {
    this.history.clear()
  }
}