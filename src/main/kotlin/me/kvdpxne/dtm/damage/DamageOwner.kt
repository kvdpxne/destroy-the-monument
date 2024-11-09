package me.kvdpxne.dtm.damage

import me.kvdpxne.dtm.shared.PlayerUuid
import me.kvdpxne.dtm.shared.debug.Debug

class DamageOwner(
  // @formatter:off
  val owner  : PlayerUuid,
  val history: MutableSet<Damage> = mutableSetOf(),
  // @formatter:on
) {

  fun addDamage(
    attacker: PlayerUuid,
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