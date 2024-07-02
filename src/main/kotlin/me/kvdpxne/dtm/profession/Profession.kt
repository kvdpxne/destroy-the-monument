package me.kvdpxne.dtm.profession

import java.util.UUID
import me.kvdpxne.dtm.DestroyTheMonument
import me.kvdpxne.dtm.gui.SlotItem
import me.kvdpxne.dtm.shared.toBuilder
import org.bukkit.Bukkit
import org.bukkit.DyeColor
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.potion.PotionEffect
import org.bukkit.scheduler.BukkitRunnable

class Profession(
  // @formatter:off
  var name       : String,
  var displayName: String,
  var items      : List<SlotItem>,
  var icon       : ItemStack,
  var effect     : PotionEffect? = null,
  var ability    : Ability? = null,
  val identifier : UUID = UUID.randomUUID()
  // @formatter:on
) {

  init {
    if (displayName.isBlank()) {
      displayName = name
    }
  }

  fun equip(player: Player, dyeColor: DyeColor) {
    items.forEach {
      player.inventory.setItem(
        it.index,
        it.item.toBuilder()
          // TODO NBT problem
          // if item already has NBT defined and its itemMeta is edited NBT is lost
          .leather(dyeColor.color)
          .unbreakable()
          .build()
      )
    }

    this.addEffect(player)
  }

  fun addEffect(player: Player) {
    if (null == this.effect) {
      return
    }

    Bukkit.getScheduler().runTaskLaterAsynchronously(
      DestroyTheMonument.instance,
      {
        player.addPotionEffect(this.effect, true)
      },
      4L
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