package me.kvdpxne.dtm.listener

import me.kvdpxne.dtm.game.GameManager
import me.kvdpxne.dtm.user.UserManager
import org.bukkit.entity.Arrow
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.ProjectileHitEvent

object ProjectileHitListener : Listener {

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

    val teammate = team.findTeammate(user) ?: return

    // Current profession
    val profession = teammate.professionQueuingPair.current

    if (profession.name != "archer") {
      return
    }

    if (!profession.ability?.ready!!) {
      return
    }

    projectile.apply {
      val location = this.location
      this.world.createExplosion(
        location.x,
        location.y,
        location.z,
        3.875F,
        false,
        !arena.revivalPositions.any {
          it.inSpawnRange(location.x, location.y, location.z, 10.0)
        }
      )
    }

    profession.ability?.run(shooter)
  }
}