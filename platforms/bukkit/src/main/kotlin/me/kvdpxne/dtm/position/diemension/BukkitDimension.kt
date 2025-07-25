package me.kvdpxne.dtm.position.diemension

import java.lang.ref.WeakReference
import java.util.UUID
import me.kvdpxne.dtm.shared.WorldLoader
import org.bukkit.Bukkit
import org.bukkit.World

open class BukkitDimension(
  // @formatter:off
  identifier: UUID,
  name      : String
  // @formatter:on
) :
  BasicDimension(
    identifier,
    name
  ) {

  companion object {

    /**
     * @since 0.1.0
     */
    @Suppress("ConstPropertyName")
    private const val serialVersionUID: Long = 2860477911668918758L
  }

  private val lazyWorld: World? by lazy {
    var world: World? = this.worldReference?.get() as World
    if (null != world) {
      return@lazy world
    }

    world = Bukkit.getWorld(this.identifier)
    if (null != world) {
      this.worldReference = WeakReference(world)
      return@lazy world
    }

    world = WorldLoader.getWorld(this.name)
    if (null != world) {
      this.worldReference = WeakReference(world)
      return@lazy world
    }

    return@lazy null
  }

  override fun getWorld(): World? {
    return this.lazyWorld
  }
}