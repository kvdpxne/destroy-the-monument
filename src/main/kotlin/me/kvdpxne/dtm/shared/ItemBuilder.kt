package me.kvdpxne.dtm.shared

import me.kvdpxne.dtm.colorize
import me.kvdpxne.dtm.colorizeAll
import org.bukkit.Color
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.LeatherArmorMeta

object Attributes {

  // Attributes
  const val ATTACK_DAMAGE = "generic.attackDamage"
  const val KNOCKBACK_RESISTANCE = "generic.knockbackResistance"
  const val FOLLOW_RANGE = "generic.followRange"
  const val MAX_HEALTH = "generic.maxHealth"

  // Operations
  const val FLAT = 0
  const val ADDITIVE = 1
  const val MULTIPLICATIVE = 2
}

class ItemBuilder {

  private var itemStack: ItemStack? = null

  fun item(item: ItemStack): ItemBuilder {
    this.itemStack = item
    return this
  }

  fun item(material: Material): ItemBuilder {
    this.itemStack = ItemStack(material)
    return this
  }

  fun type(material: Material): ItemBuilder {
    this.itemStack?.type = material
    return this
  }

  fun quantity(quantity: Int): ItemBuilder {
    this.itemStack?.amount = quantity
    return this
  }

  fun damage(durability: Int): ItemBuilder {
    this.itemStack?.durability = durability.toShort()
    return this
  }

  fun name(name: String): ItemBuilder {
    this.itemStack?.itemMeta = this.itemStack?.itemMeta.apply {
      this?.displayName = name.colorize()
    }
    return this;
  }

  fun lore(vararg lore: String): ItemBuilder {
    this.itemStack?.itemMeta = this.itemStack?.itemMeta.apply {
      this?.lore = arrayOf(*lore).colorizeAll()
    }
    return this
  }

  fun leather(color: Color): ItemBuilder {
    val itemMeta = this.itemStack?.itemMeta
    if (itemMeta is LeatherArmorMeta) {
      itemMeta.color = color
      this.itemStack?.itemMeta = itemMeta
    }
    return this
  }

  fun enchantment(enchantment: Enchantment, level: Int): ItemBuilder {
    this.itemStack?.addUnsafeEnchantment(enchantment, level)
    return this
  }

  fun unbreakable(): ItemBuilder {
    if (0.toShort() == this.itemStack?.type?.maxDurability) {
      return this
    }

    this.itemStack = BukkitItemStack.asNMSCopy(this.itemStack).apply {
      val base = getTag() ?: MinecraftNBTTagCompound()

      base.setByte("Unbreakable", 1)
      setTag(base)
    }.let {
      BukkitItemStack.asCraftMirror(it)
    }
    return this
  }

  fun attribute(
    name: String,
    amount: Double,
    operation: Int = Attributes.FLAT
  ): ItemBuilder {
    this.itemStack = BukkitItemStack.asNMSCopy(itemStack).apply {

      val base = getTag() ?: MinecraftNBTTagCompound()
      val attributeModifiers = base["AttributeModifiers"]?.let { it as MinecraftNBTTagList } ?: MinecraftNBTTagList()

      attributeModifiers.add(MinecraftNBTTagCompound().apply {
        setString("AttributeName", name)
        setString("Name", name)
        setDouble("Amount", amount)
        setInt("Operation", operation)
        setInt("UUIDLeast", (100_000..999_999).random())
        setInt("UUIDMost", (10_000..99_999).random());
      })

      base.set("AttributeModifiers", attributeModifiers)
      setTag(base)
    }.let {
      BukkitItemStack.asCraftMirror(it)
    }
    return this
  }

  fun build(): ItemStack {
    return this.itemStack!!
  }
}