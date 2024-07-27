package me.kvdpxne.dtm.shared.bukkit

import java.util.UUID
import me.kvdpxne.dtm.colorize
import me.kvdpxne.dtm.colorizeAll
import me.kvdpxne.dtm.shared.minecraft.BukkitItemStack
import me.kvdpxne.dtm.shared.minecraft.MinecraftNBTTagCompound
import me.kvdpxne.dtm.shared.minecraft.MinecraftNBTTagList
import org.bukkit.Color
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.LeatherArmorMeta
import org.bukkit.inventory.meta.PotionMeta
import org.bukkit.potion.PotionEffect

object Attributes {

  // Attributes
  const val ATTACK_DAMAGE = "generic.attackDamage"
  const val KNOCKBACK_RESISTANCE = "generic.knockbackResistance"
  const val FOLLOW_RANGE = "generic.followRange"
  const val MAX_HEALTH = "generic.maxHealth"

  // Operations
  const val ADD = 0
  const val MULTIPLY_BASE = 1
  const val MULTIPLY = 2
}

class ItemBuilder private constructor(private var itemStack: ItemStack) {

  companion object {

    /**
     * @since 0.1.0
     */
    fun begin(itemStack: ItemStack): ItemBuilder {
      return ItemBuilder(itemStack)
    }
  }

  @Deprecated("")
  fun item(item: ItemStack): ItemBuilder {
    this.itemStack = item
    return this
  }

  @Deprecated("")
  fun item(material: Material): ItemBuilder {
    this.itemStack = ItemStack(material)
    return this
  }

  /**
   * @since 0.1.0
   */
  fun type(
    type: Material
  ): ItemBuilder {
    this.itemStack.type = type
    return this
  }

  /**
   * @since 0.1.0
   */
  fun generation(
    generation: Int
  ): ItemBuilder {
    if (0 < this.itemStack.type.maxDurability) {
      throw IllegalArgumentException("This item has no other generations.")
    }

    this.itemStack.durability = generation.toShort()
    return this
  }

  /**
   * @since 0.1.0
   */
  fun amount(
    amount: Int
  ): ItemBuilder {
    this.itemStack.amount = amount
    return this
  }

  /**
   * @since 0.1.0
   */
  fun durability(
    durability: Int
  ): ItemBuilder {
    if (0 >= this.itemStack.type.maxDurability) {
      throw IllegalStateException("The item has no durability.")
    }

    this.itemStack.durability = durability.toShort()
    return this
  }

  /**
   * @since 0.1.0
   */
  fun name(
    name: String
  ): ItemBuilder {
    this.itemStack.itemMeta = this.itemStack.itemMeta.apply {
      this.displayName = name.colorize()
    }
    return this
  }

  /**
   * @since 0.1.0
   */
  fun name(
    name: () -> String
  ): ItemBuilder {
    return this.name(name())
  }

  /**
   * @since 0.1.0
   */
  fun lore(
    vararg lore: String
  ): ItemBuilder {
    this.itemStack.itemMeta = this.itemStack.itemMeta.apply {
      this.lore = arrayOf(*lore).colorizeAll()
    }
    return this
  }

  /**
   * @since 0.1.0
   */
  fun leather(
    color: Color
  ): ItemBuilder {
    val itemMeta = this.itemStack.itemMeta
    if (itemMeta !is LeatherArmorMeta) {
      return this
    }
    itemMeta.color = color
    this.itemStack.itemMeta = itemMeta
    return this
  }

  /**
   * @since 0.1.0
   */
  fun potionEffect(
    potionEffect: PotionEffect,
    override: Boolean = true
  ): ItemBuilder {
    val itemMeta = this.itemStack.itemMeta
    if (itemMeta !is PotionMeta) {
      return this
    }
    itemMeta.addCustomEffect(potionEffect, override)
    this.itemStack.itemMeta = itemMeta
    return this
  }

  /**
   * @since 0.1.0
   */
  fun enchantment(
    enchantment: Enchantment,
    level: Int
  ): ItemBuilder {
    this.itemStack.addUnsafeEnchantment(enchantment, level)
    return this
  }

  /**
   * @since 0.1.0
   */
  fun attribute(
    name: String,
    amount: Double,
    operation: Int = Attributes.ADD
  ): ItemBuilder {
    this.itemStack = BukkitItemStack.asNMSCopy(this.itemStack).apply {
      if (!this.hasTag()) {
        this.tag = MinecraftNBTTagCompound()
      }

      if (!this.tag.hasKeyOfType("AttributeModifiers", 9)) {
        this.tag.set("AttributeModifiers", MinecraftNBTTagList())
      }

      val attributeModifiers = this.tag.getList("AttributeModifiers", 10)

      val newAttributeModifier = MinecraftNBTTagCompound().apply {
        this.setString("AttributeName", name)
        this.setString("Name", name)
        this.setDouble("Amount", amount)
        this.setInt("Operation", operation)

        val uuid = UUID.randomUUID()
        this.setLong("UUIDMost", uuid.mostSignificantBits)
        this.setLong("UUIDLeast", uuid.leastSignificantBits)
      }

      attributeModifiers.add(newAttributeModifier)
    }.let {
      BukkitItemStack.asBukkitCopy(it)
    }
    return this
  }

  /**
   * @since 0.1.0
   */
  fun unbreakable(): ItemBuilder {
    if (0.toShort() == this.itemStack.type.maxDurability) {
      return this
    }

    this.itemStack = BukkitItemStack.asNMSCopy(this.itemStack).apply {
      if (!this.hasTag()) {
        this.tag = MinecraftNBTTagCompound()
      }

      this.tag.setByte("Unbreakable", 1.toByte())
    }.let {
      BukkitItemStack.asBukkitCopy(it)
    }
    return this
  }

  /**
   * @since 0.1.0
   */
  fun build(): ItemStack {
    return this.itemStack
  }
}