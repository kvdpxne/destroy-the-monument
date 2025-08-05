package me.kvdpxne.dtm.profession

import java.util.UUID
import me.kvdpxne.dtm.shared.item.hasDurability
import me.kvdpxne.dtm.shared.item.isLeatherArmor
import me.kvdpxne.dtm.shared.task.runSynchronousDelayedTask
import me.kvdpxne.dtm.shared.item.toBuilder
import org.bukkit.DyeColor
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.potion.PotionEffect

class Profession(
  // @formatter:off
  var name       : String,
  var displayName: String,
  var items      : List<SlotItem>,
  var icon       : ItemStack,
  var enabled    : Boolean       = true,
  var effect     : PotionEffect? = null,
  var ability    : Ability?      = null,
  val identifier : UUID          = UUID.randomUUID()
  // @formatter:on
) : Cloneable {

  init {
    if (displayName.isBlank()) {
      displayName = name
    }
  }

  /**
   * @since 0.1.0
   */
  private fun colourArmour(
    item: SlotItem,
    dyeColor: DyeColor
  ): ItemStack {
    if (!item.item.hasDurability()) {
      return item.item
    }

    val builder = item.item.toBuilder()
    if (item.index in 36..39 && item.item.isLeatherArmor()) {
      builder.leather(dyeColor.color)
    }

    return builder.unbreakable().build()
  }

  /**
   * @since 0.1.0
   */
  fun equip(
    player: Player,
    dyeColor: DyeColor
  ) {
    if (null != this.effect) {
      runSynchronousDelayedTask(4L) {
        player.addPotionEffect(this.effect, true)
      }
    }

    this.items.forEach {
      player.inventory.setItem(
        it.index,
        this.colourArmour(it, dyeColor)
      )
    }
  }

  public override fun clone(): Profession {
    return Profession(
      this.name,
      this.displayName,
      this.items,
      this.icon,
      this.enabled,
      this.effect,
      this.ability?.clone(),
      this.identifier
    )
  }

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as Profession

    if (name != other.name) return false
    if (identifier != other.identifier) return false

    return true
  }

  override fun hashCode(): Int {
    var result = name.hashCode()
    result = 31 * result + identifier.hashCode()
    return result
  }

  override fun toString(): String {
    return "Profession(name='$name', identifier=$identifier)"
  }
}