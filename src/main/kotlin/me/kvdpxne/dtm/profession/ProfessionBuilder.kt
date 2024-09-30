package me.kvdpxne.dtm.profession

import java.util.UUID
import me.kvdpxne.dtm.gui.SlotItem
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

class ProfessionBuilder {

  // @formatter:off
  private          var identifier : UUID
  private lateinit var name       : String
  private lateinit var displayName: String
  private lateinit var items      : Array<out SlotItem>
  private lateinit var icon       : ItemStack
  private          var enabled    : Boolean?
  private          var effect     : PotionEffect?
  private          var ability    : Ability?
  // @formatter:on

  init {
    this.effect = null
    this.ability = null
    this.enabled = null
    this.identifier = UUID.randomUUID()
  }

  fun name(name: String): ProfessionBuilder {
    this.name = name
    return this
  }

  fun displayName(displayName: String): ProfessionBuilder {
    this.displayName = displayName
    return this
  }

  fun items(vararg items: SlotItem): ProfessionBuilder {
    this.items = items
    return this
  }

  fun icon(icon: ItemStack): ProfessionBuilder {
    this.icon = icon
    return this
  }

  fun icon(material: Material): ProfessionBuilder {
    return this.icon(ItemStack(material))
  }

  fun enabled(): ProfessionBuilder {
    this.enabled = true
    return this
  }

  fun disabled(): ProfessionBuilder {
    this.enabled = false
    return this
  }

  fun effect(effect: PotionEffect): ProfessionBuilder {
    this.effect = effect
    return this
  }

  fun effect(type: PotionEffectType, level: Int = 0, ambient: Boolean = false): ProfessionBuilder {
    return this.effect(PotionEffect(type, Int.MAX_VALUE, level, ambient))
  }

  fun ability(delay: Int, activatable: Boolean, whenReady: WhenAbilityReadyHandler = {}): ProfessionBuilder {
    this.ability = Ability(delay, whenReady, isActivatable = activatable)
    return this
  }

  fun build() : Profession {
    return Profession(
      name,
      displayName,
      items.toMutableList(),
      icon,
      this.enabled!!,
      effect,
      this.ability,
      identifier,
    )
  }
}