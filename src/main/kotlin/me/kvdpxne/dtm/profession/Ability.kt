package me.kvdpxne.dtm.profession

import org.bukkit.potion.PotionEffect

class Ability(
  val statusEffect: PotionEffect?
) {

  companion object {
    val NONE = Ability(null)
  }
}