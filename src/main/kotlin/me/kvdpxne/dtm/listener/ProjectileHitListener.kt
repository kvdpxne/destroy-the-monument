package me.kvdpxne.dtm.listener

import kotlin.random.Random
import me.kvdpxne.dtm.game.GameManager
import me.kvdpxne.dtm.user.UserManager
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.entity.Arrow
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.ProjectileHitEvent

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
  fun handleProjectileHit(event: ProjectileHitEvent) {
    val projectile = event.entity
    if (projectile !is Arrow) {
      return
    }

    @Suppress("DEPRECATION")
    val shooter = projectile.shooter
    if (shooter !is Player) {
      return
    }

    //
    val user = UserManager.findByIdentifier(shooter.uniqueId) ?: return

    //
    val game = GameManager.findByUser(user) ?: return

    val arena = game.currentArena ?: return

    if (
      !game.state.isStarted() ||
      null == game.currentArena ||
      !game.isInTeam(user) ||
      !game.isInArenaMap(user)
    ) {
      return
    }

    //
    val team = game.findTeam(user) ?: return

    //
    val teammate = team.findTeammate(user) ?: return

    // Current profession
    val profession = teammate.professionQueuingPair.current

    //
    if (false == profession.ability?.isReady) {
      return
    }

    if (profession.name == "archer") {
      val location = projectile.location
      this.createExplosion(
        location,
        3.975F,
        !arena.revivalPositions.any {
          it.inSpawnRange(location.x, location.y, location.z, 14.5)
        }
      )
      profession.ability?.renewDelayed(shooter)
      return
    }

    if (profession.name == "pyro") {
      this.createConflagration(
        projectile.location,
        6
      )
      profession.ability?.renewDelayed(shooter)
      return
    }
  }
}