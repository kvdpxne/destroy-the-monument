package me.kvdpxne.dtm.shared.item

import kotlin.random.Random
import me.kvdpxne.dtm.shared.attributes.GenericAttribute
import me.kvdpxne.dtm.shared.attributes.Operations
import me.kvdpxne.dtm.shared.reflection.FieldAccessor
import me.kvdpxne.dtm.shared.reflection.MethodInvoker
import me.kvdpxne.dtm.shared.reflection.PrimitiveTypes
import me.kvdpxne.dtm.shared.reflection.Reflection
import me.kvdpxne.dtm.shared.text.colorize
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
    if (this.itemStack.hasDurability()) {
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
    if (!this.itemStack.hasDurability()) {
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

  fun enchantmentEffect(): ItemBuilder {
    val craftItemStackClass: Class<*> = Reflection.getCraftBukkitClass("inventory.CraftItemStack")

    val minecraftItemStack: Any = Reflection
      .getMethod(craftItemStackClass, "asNMSCopy", null, arrayOf(this.itemStack.javaClass))
      .invoke(null, this.itemStack)!!

    val nbtTagCompoundFieldAccessor: FieldAccessor = Reflection.getField(minecraftItemStack.javaClass, "tag")
    var nbtTagCompound: Any? = nbtTagCompoundFieldAccessor.get(minecraftItemStack)

    val nbtTagCompoundClass: Class<*> = if (null == nbtTagCompound) {
      val nbtTagCompoundClass: Class<*> = Reflection.getMinecraftClass("NBTTagCompound")
      val newNbtTagCompoundClass: Any = Reflection.getConstructor(nbtTagCompoundClass).invoke()

      nbtTagCompound = newNbtTagCompoundClass
      nbtTagCompoundFieldAccessor.set(minecraftItemStack, newNbtTagCompoundClass)

      nbtTagCompoundClass
    } else {
      nbtTagCompound.javaClass
    }

    val clazz: Class<*> = Reflection.getMinecraftClass("NBTTagList")
    val nbtTagList: Any = Reflection.getConstructor(clazz).invoke()

    val nbtTagBaseClass: Class<*> = Reflection.getMinecraftClass("NBTBase")

    Reflection
      .getMethod(nbtTagCompoundClass, "set", null, arrayOf(PrimitiveTypes.STRING, nbtTagBaseClass))
      .invoke(nbtTagCompound, "ench", nbtTagList)

    this.itemStack = Reflection
      .getMethod(craftItemStackClass, "asBukkitCopy", null, arrayOf(minecraftItemStack.javaClass))
      .invoke(null, minecraftItemStack) as ItemStack

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

    val craftItemStackClass: Class<*> = Reflection.getCraftBukkitClass("inventory.CraftItemStack")

    val minecraftItemStack: Any = Reflection
      .getMethod(craftItemStackClass, "asNMSCopy", null, arrayOf(this.itemStack.javaClass))
      .invoke(null, this.itemStack)!!

    val nbtTagCompoundFieldAccessor: FieldAccessor = Reflection.getField(minecraftItemStack.javaClass, "tag")
    var nbtTagCompound: Any? = nbtTagCompoundFieldAccessor.get(minecraftItemStack)

    val nbtTagCompoundClass: Class<*> = if (null == nbtTagCompound) {
      val nbtTagCompoundClass: Class<*> = Reflection.getMinecraftClass("NBTTagCompound")
      val newNbtTagCompoundClass: Any = Reflection.getConstructor(nbtTagCompoundClass).invoke()

      nbtTagCompound = newNbtTagCompoundClass
      nbtTagCompoundFieldAccessor.set(minecraftItemStack, newNbtTagCompoundClass)

      nbtTagCompoundClass
    } else {
      nbtTagCompound.javaClass
    }

    val hasKeyOfType: Boolean = Reflection.getMethod(nbtTagCompoundClass, "hasKeyOfType", PrimitiveTypes.BOOLEAN, arrayOf(PrimitiveTypes.STRING, PrimitiveTypes.INT))
      .invoke(nbtTagCompound,"AttributeModifiers", 9) as Boolean

    val nbtTagList: Any
    val nbtTagListClass: Class<*> = if (!hasKeyOfType) {
      val clazz: Class<*> = Reflection.getMinecraftClass("NBTTagList")
      val fs: Any = Reflection.getConstructor(clazz).invoke()

      nbtTagList = fs
      clazz
    } else {
      nbtTagList = Reflection.getMethod(nbtTagCompoundClass, "get", null, arrayOf(PrimitiveTypes.STRING))
        .invoke(nbtTagCompound, "AttributeModifiers") as Any

      nbtTagList.javaClass
    }

    val newNbtTagCompoundClass: Any = Reflection.getConstructor(nbtTagCompoundClass).invoke()

    val setStringMethodInvoker: MethodInvoker = Reflection
      .getMethod(nbtTagCompoundClass,"setString", null, arrayOf(PrimitiveTypes.STRING, PrimitiveTypes.STRING))

    setStringMethodInvoker.invoke(newNbtTagCompoundClass,"AttributeName", attribute)
    setStringMethodInvoker.invoke(newNbtTagCompoundClass,"Name", attribute)

    Reflection
      .getMethod(nbtTagCompoundClass, "setDouble", null, arrayOf(PrimitiveTypes.STRING, PrimitiveTypes.DOUBLE))
      .invoke(newNbtTagCompoundClass,"Amount", amount)

    Reflection
      .getMethod(nbtTagCompoundClass, "setInt", null, arrayOf(PrimitiveTypes.STRING, PrimitiveTypes.INT))
      .invoke(newNbtTagCompoundClass,"Operation", operation)

    val setLongMethodInvoker: MethodInvoker = Reflection
      .getMethod(nbtTagCompoundClass, "setLong", null, arrayOf(PrimitiveTypes.STRING, PrimitiveTypes.LONG))

    val time: Long = System.currentTimeMillis()

    setLongMethodInvoker.invoke(newNbtTagCompoundClass, "UUIDLeast", Random.nextLong())
    setLongMethodInvoker.invoke(newNbtTagCompoundClass,"UUIDMost", time.shl(16).or(Random.nextLong().and(65535L)))

    val nbtTagBaseClass: Class<*> = Reflection.getMinecraftClass("NBTBase")

    Reflection
      .getMethod(nbtTagListClass, "add", null, arrayOf(nbtTagBaseClass))
      .invoke(nbtTagList, newNbtTagCompoundClass)

    Reflection
      .getMethod(nbtTagCompoundClass, "set", null, arrayOf(PrimitiveTypes.STRING, nbtTagBaseClass))
      .invoke(nbtTagCompound, "AttributeModifiers", nbtTagList)

    this.itemStack = Reflection
      .getMethod(craftItemStackClass, "asBukkitCopy", null, arrayOf(minecraftItemStack.javaClass))
      .invoke(null, minecraftItemStack) as ItemStack

    return this
  }

  /**
   * @since 0.1.0
   */
  fun clearAttributes(): ItemBuilder {
    val craftItemStackClass: Class<*> = Reflection.getCraftBukkitClass("inventory.CraftItemStack")

    val minecraftItemStack: Any = Reflection
      .getMethod(craftItemStackClass, "asNMSCopy", null, arrayOf(this.itemStack.javaClass))
      .invoke(null, this.itemStack)!!

    val nbtTagCompoundFieldAccessor: FieldAccessor = Reflection.getField(minecraftItemStack.javaClass, "tag")
    var nbtTagCompound: Any? = nbtTagCompoundFieldAccessor.get(minecraftItemStack)

    val nbtTagCompoundClass: Class<*> = if (null == nbtTagCompound) {
      val nbtTagCompoundClass: Class<*> = Reflection.getMinecraftClass("NBTTagCompound")
      val newNbtTagCompoundClass: Any = Reflection.getConstructor(nbtTagCompoundClass).invoke()

      nbtTagCompound = newNbtTagCompoundClass
      nbtTagCompoundFieldAccessor.set(minecraftItemStack, newNbtTagCompoundClass)

      nbtTagCompoundClass
    } else {
      nbtTagCompound.javaClass
    }

    val clazz: Class<*> = Reflection.getMinecraftClass("NBTTagList")
    val nbtTagList: Any = Reflection.getConstructor(clazz).invoke()

    val nbtTagBaseClass: Class<*> = Reflection.getMinecraftClass("NBTBase")

    Reflection
      .getMethod(nbtTagCompoundClass, "set", null, arrayOf(PrimitiveTypes.STRING, nbtTagBaseClass))
      .invoke(nbtTagCompound, "AttributeModifiers", nbtTagList)

    this.itemStack = Reflection
      .getMethod(craftItemStackClass, "asBukkitCopy", null, arrayOf(minecraftItemStack.javaClass))
      .invoke(null, minecraftItemStack) as ItemStack

    return this
  }

  /**
   * @since 0.1.0
   */
  fun unbreakable(): ItemBuilder {
    if (!this.itemStack.hasDurability()) {
      return this
    }

    val craftItemStackClass: Class<*> = Reflection.getCraftBukkitClass("inventory.CraftItemStack")

    val minecraftItemStack: Any = Reflection
      .getMethod(craftItemStackClass, "asNMSCopy", null, arrayOf(this.itemStack.javaClass))
      .invoke(null, this.itemStack)!!

    val nbtTagCompoundFieldAccessor: FieldAccessor = Reflection.getField(minecraftItemStack.javaClass, "tag")
    var nbtTagCompound: Any? = nbtTagCompoundFieldAccessor.get(minecraftItemStack)

    val nbtTagCompoundClass: Class<*> = if (null == nbtTagCompound) {
      val nbtTagCompoundClass: Class<*> = Reflection.getMinecraftClass("NBTTagCompound")
      val newNbtTagCompoundClass: Any = Reflection.getConstructor(nbtTagCompoundClass).invoke()

      nbtTagCompound = newNbtTagCompoundClass
      nbtTagCompoundFieldAccessor.set(minecraftItemStack, newNbtTagCompoundClass)

      nbtTagCompoundClass
    } else {
      nbtTagCompound.javaClass
    }

    Reflection
      .getMethod(nbtTagCompoundClass, "setByte", null, arrayOf(PrimitiveTypes.STRING, PrimitiveTypes.BYTE))
      .invoke(nbtTagCompound, "Unbreakable", 1.toByte())

    this.itemStack = Reflection
      .getMethod(craftItemStackClass, "asBukkitCopy", null, arrayOf(minecraftItemStack.javaClass))
      .invoke(null, minecraftItemStack) as ItemStack

    return this
  }

  /**
   * @since 0.1.0
   */
  fun build(): ItemStack {
    return this.itemStack
  }
}