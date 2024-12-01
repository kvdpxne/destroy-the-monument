package me.kvdpxne.dtm.container

import me.kvdpxne.dtm.shared.reflection.Reflection

/**
 * @param item
 *
 * @since 0.1.0
 */
internal fun requireMinecraftItemStack(
  item: Any
) {
  val clazz: Class<*> = Reflection.getMinecraftClass("ItemStack")
  require(clazz.isInstance(item)) {
    "The passed item must be an instance of the ${clazz.name} class."
  }
}