package me.kvdpxne.dtm.shared.item

import java.lang.reflect.Constructor
import java.lang.reflect.Field
import java.lang.reflect.Method
import kotlin.random.Random
import me.kvdpxne.dtm.shared.text.colorize
import me.kvdpxne.dtm.shared.attributes.GenericAttribute
import me.kvdpxne.dtm.shared.attributes.Operations
import me.kvdpxne.dtm.shared.reflection.Reflection
import org.bukkit.Color
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.LeatherArmorMeta
import org.bukkit.inventory.meta.PotionMeta
import org.bukkit.potion.PotionEffect

class ItemBuilder private constructor(
  private var itemStack: ItemStack
) {

  companion object {

    /**
     * @since 0.1.0
     */
    fun begin(itemStack: ItemStack): ItemBuilder {
      return ItemBuilder(itemStack)
    }
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
      this.displayName = name.colorize
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
      this.lore = listOf(*lore).colorize as List<String>
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
    attribute: GenericAttribute,
    amount: Double,
    operation: Int = Operations.ADD
  ): ItemBuilder {
    // org.bukkit.craftbukkit.v1_7_R4.inventory.CraftItemStack
    val craftItemStackClass: Class<*> = Reflection.getCraftBukkitClass("inventory.CraftItemStack")
    val asNMSCopyMethod: Method = craftItemStackClass.getMethod("asNMSCopy", this.itemStack.javaClass)

    // net.minecraft.server.v1_7_R4.ItemStack
    val nmsItemStack: Any = asNMSCopyMethod.invoke(null, this.itemStack)
    val nmsItemStackClass: Class<*> = nmsItemStack.javaClass
    val tagField: Field = nmsItemStackClass.getField("tag")
    val hasTagMethod: Method = nmsItemStackClass.getMethod("hasTag")
    val hasTag: Boolean = hasTagMethod.invoke(nmsItemStack) as Boolean

    // net.minecraft.server.v1_7_R4.NBTTagCompound
    val nbtTagCompoundClass: Class<*> = Reflection.getNmsClass("NBTTagCompound")
    val nbtTagCompoundConstructor: Constructor<*> = nbtTagCompoundClass.getConstructor()

    if (!hasTag) {
      val nbtTagCompound: Any = nbtTagCompoundConstructor.newInstance()
      tagField.set(nmsItemStack, nbtTagCompound)
    }

    val tag: Any = tagField.get(nmsItemStack)
    val tagClass: Class<*> = tag.javaClass
    val hasKeyOfTypeMethod: Method = tagClass.getMethod("hasKeyOfType", String::class.java, Int::class.java)
    val hasKeyOfType: Boolean = hasKeyOfTypeMethod.invoke(tag, "AttributeModifiers", 9) as Boolean

    // net.minecraft.server.v1_7_R4.NBTBase
    val nbtBaseClass: Class<*> = Reflection.getNmsClass("NBTBase")

    if (!hasKeyOfType) {
      // net.minecraft.server.v1_7_R4.NBTTagList
      val nbtTagListClass: Class<*> = Reflection.getNmsClass("NBTTagList")
      val nbtTagListConstructor: Constructor<*> = nbtTagListClass.getConstructor()
      val nbtTagList: Any = nbtTagListConstructor.newInstance()

      val setMethod: Method = tagClass.getMethod("set", String::class.java, nbtBaseClass)
      setMethod.invoke(tag, "AttributeModifiers", nbtTagList)
    }

    val nbtTagCompound: Any = nbtTagCompoundConstructor.newInstance()
    val setStringMethod: Method = nbtTagCompoundClass.getMethod("setString", String::class.java, String::class.java)
    val setDoubleMethod: Method = nbtTagCompoundClass.getMethod("setDouble", String::class.java, Double::class.java)
    val setIntMethod: Method = nbtTagCompoundClass.getMethod("setInt", String::class.java, Int::class.java)
    val setLongMethod: Method = nbtTagCompoundClass.getMethod("setLong", String::class.java, Long::class.java)

    setStringMethod.invoke(nbtTagCompound, "AttributeName", attribute)
    setStringMethod.invoke(nbtTagCompound, "Name", attribute)
    setDoubleMethod.invoke(nbtTagCompound, "Amount", amount)
    setIntMethod.invoke(nbtTagCompound, "Operation", operation)

    val time: Long = System.currentTimeMillis()

    setLongMethod.invoke(nbtTagCompound, "UUIDLeast", Random.nextLong())
    setLongMethod.invoke(nbtTagCompound, "UUIDMost", time.shl(16).or(Random.nextLong().and(65535L)))

    val getListMethod: Method = tagClass.getMethod("getList", String::class.java, Int::class.java)

    val nbtTagList: Any = getListMethod.invoke(tag, "AttributeModifiers", 10) as Any
    val nbtTagListClass: Class<*> = nbtTagList.javaClass
    val addMethod: Method = nbtTagListClass.getMethod("add", nbtBaseClass)

    addMethod.invoke(nbtTagList, nbtTagCompound)

    val asBukkitCopyMethod: Method = craftItemStackClass.getMethod("asBukkitCopy", nmsItemStackClass)
    val bukkitItemStack: ItemStack = asBukkitCopyMethod.invoke(null, nmsItemStack) as ItemStack

    this.itemStack = bukkitItemStack
    return this
  }

  /**
   * @since 0.1.0
   */
  fun unbreakable(): ItemBuilder {
    if (0.toShort() == this.itemStack.type.maxDurability) {
      return this
    }

    // org.bukkit.craftbukkit.v1_7_R4.inventory.CraftItemStack
    val craftItemStackClass: Class<*> = Reflection.getCraftBukkitClass("inventory.CraftItemStack")
    val asNMSCopyMethod: Method = craftItemStackClass.getMethod("asNMSCopy", this.itemStack.javaClass)

    // net.minecraft.server.v1_7_R4.ItemStack
    val nmsItemStack: Any = asNMSCopyMethod.invoke(null, this.itemStack)
    val nmsItemStackClass: Class<*> = nmsItemStack.javaClass
    val tagField: Field = nmsItemStackClass.getField("tag")
    val hasTagMethod: Method = nmsItemStackClass.getMethod("hasTag")
    val hasTag: Boolean = hasTagMethod.invoke(nmsItemStack) as Boolean

    if (!hasTag) {
      // net.minecraft.server.v1_7_R4.NBTTagCompound
      val nbtTagCompoundClass: Class<*> = Reflection.getNmsClass("NBTTagCompound")
      val nbtTagCompoundConstructor: Constructor<*> = nbtTagCompoundClass.getConstructor()
      val nbtTagCompound: Any = nbtTagCompoundConstructor.newInstance()

      tagField.set(nmsItemStack, nbtTagCompound)
    }

    val nbtTagCompound: Any = tagField.get(nmsItemStack)
    val nbtTagCompoundClass: Class<*> = nbtTagCompound.javaClass
    val setByteMethod: Method = nbtTagCompoundClass.getMethod("setByte", String::class.java, Byte::class.java)

    setByteMethod.invoke(nbtTagCompound, "Unbreakable", 1.toByte())

    val asBukkitCopyMethod: Method = craftItemStackClass.getMethod("asBukkitCopy", nmsItemStackClass)
    val bukkitItemStack: ItemStack = asBukkitCopyMethod.invoke(null, nmsItemStack) as ItemStack

    this.itemStack = bukkitItemStack
    return this
  }

  /**
   * @since 0.1.0
   */
  fun build(): ItemStack {
    return this.itemStack
  }
}