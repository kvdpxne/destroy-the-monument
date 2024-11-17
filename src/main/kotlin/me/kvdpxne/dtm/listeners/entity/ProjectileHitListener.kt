package me.kvdpxne.dtm.listeners.entity

import kotlin.random.Random
import me.kvdpxne.dtm.arena.Arena
import me.kvdpxne.dtm.configuration.GeneralConfiguration
import me.kvdpxne.dtm.game.LocalGame
import me.kvdpxne.dtm.profession.Profession
import me.kvdpxne.dtm.shared.player.localUser
import me.kvdpxne.dtm.shared.reflection.Reflection
import me.kvdpxne.dtm.user.LocalUser
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.entity.Arrow
import org.bukkit.entity.Player
import org.bukkit.entity.Projectile
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.ProjectileHitEvent
import org.bukkit.projectiles.ProjectileSource

object ProjectileHitListener : Listener {

  fun sphere(
    center: Location,
    radius: Int
  ): List<Block> {
    val blocks = mutableListOf<Block>()

    val world = center.world

    val centerX = center.blockX
    val centerY = center.blockY
    val centerZ = center.blockZ

    val minX = centerX - radius
    val maxX = centerX + radius
    val minY = centerY - radius
    val maxY = centerY + radius
    val minZ = centerZ - radius
    val maxZ = centerZ + radius

    val radiusSquared = radius * radius

    for (x in minX..maxX) {
      for (y in minY..maxY) {
        for (z in minZ..maxZ) {

          val dx = x - centerX
          val dy = y - centerY
          val dz = z - centerZ

          if (radiusSquared >= dx * dx + dy * dy + dz * dz) {
            val block = world.getBlockAt(x, y, z)
            blocks.add(block)
          }
        }
      }
    }

    return blocks
  }

  /**
   * @since 0.1.0
   */
  fun createConflagration(
    location: Location,
    radius: Int
  ) {
    for (block: Block in sphere(location, radius)) {
      //
      if (Material.AIR != block.type) {
        continue
      }

      //
      if (Random.nextBoolean()) {
        block.type = Material.FIRE
      }
    }
  }

  /**
   * @since 0.1.0
   */
  fun createExplosion(
    location: Location,
    radius: Float,
    destroyBlocks: Boolean
  ) {
    location.world.createExplosion(
      location.x,
      location.y,
      location.z,
      radius,
      false,
      destroyBlocks
    )
  }

  @EventHandler
  fun handleProjectileHit(
    event: ProjectileHitEvent
  ) {
    //
    val projectile: Projectile = event.entity

    //
    if (projectile !is Arrow) {
      return
    }

    //
    val shooter: ProjectileSource = Reflection
      .getMethod(Projectile::class.java, "getShooter", ProjectileSource::class.java)
      .invoke(projectile) as ProjectileSource

    //
    if (shooter !is Player) {
      return
    }

    //
    val user: LocalUser = shooter.localUser

    //
    val game: LocalGame = user.game ?: return

    //
    if (!game.isRunning && !game.isEnding) {
      return
    }

    //
    val arena: Arena = game.currentArena ?: return

    //
    if (!ProjectileLaunchListener.projectiles.contains(projectile.entityId)) {
      return
    }

    //
    val profession: Profession = game.findTeammateByHostage(user)
      ?.currentProfession
      ?: return

    when (profession.name) {
      "archer" -> {
        val location = projectile.location
        createExplosion(
          location,
          3.975F,
          !arena.revivalPositions.any {
            it.isNear(
              location.x,
              location.y,
              location.z,
              GeneralConfiguration.RADIUS_OF_EXPLOSION_INTERACTION + Math.PI
            )
          }
        )
      }

      "pyro" -> {
        createConflagration(
          projectile.location,
          5
        )
      }
    }

    ProjectileLaunchListener.projectiles.remove(projectile.entityId)
  }
}