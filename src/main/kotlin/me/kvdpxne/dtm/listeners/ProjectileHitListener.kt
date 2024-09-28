package me.kvdpxne.dtm.listeners

import kotlin.random.Random
import me.kvdpxne.dtm.configuration.Configuration
import me.kvdpxne.dtm.game.Arena
import me.kvdpxne.dtm.game.LocalGame
import me.kvdpxne.dtm.game.LocalTeam
import me.kvdpxne.dtm.game.Teammate
import me.kvdpxne.dtm.profession.Ability
import me.kvdpxne.dtm.profession.Profession
import me.kvdpxne.dtm.shared.minecraft.bukkit.localUser
import me.kvdpxne.dtm.user.LocalUser
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.entity.Arrow
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
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
    for (block: Block in this.sphere(location, radius)) {
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
    val projectile = event.entity
    if (projectile !is Arrow) {
      return
    }

    val shooter: ProjectileSource? = projectile.shooter
    if (shooter !is Player) {
      return
    }

    //
    val user: LocalUser = shooter.localUser ?: return

    //
    val game: LocalGame = user.game ?: return

    //
    val arena: Arena = game.currentArena ?: return

    //
    if (!game.isRunning) {
      return
    }

    //
    val team: LocalTeam = game.findTeamByHostage(user) ?: return

    //
    val teammate: Teammate = team.getTeammate(user) ?: return

    // Current profession
    val profession: Profession = teammate.currentProfession

    //
    val ability: Ability = profession.ability ?: return

    //
    if (!ability.isActive) {
      return
    }

    if ("archer" == profession.name) {
      val location = projectile.location
      this.createExplosion(
        location,
        3.975F,
        !arena.revivalPositions.any {
          it.isNear(
            location.x,
            location.y,
            location.z,
            Configuration.RADIUS_OF_EXPLOSION_INTERACTION + Math.PI
          )
        }
      )
      ability.renewDelayed(shooter)
      return
    }

    if ("pyro" == profession.name) {
      this.createConflagration(
        projectile.location,
        5
      )
      ability.renewDelayed(shooter)
      return
    }
  }
}