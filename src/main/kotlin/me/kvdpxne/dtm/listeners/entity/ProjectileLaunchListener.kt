package me.kvdpxne.dtm.listeners.entity

import me.kvdpxne.dtm.arena.Arena
import me.kvdpxne.dtm.game.LocalGame
import me.kvdpxne.dtm.profession.Ability
import me.kvdpxne.dtm.shared.player.localUser
import me.kvdpxne.dtm.shared.reflection.Reflection
import me.kvdpxne.dtm.user.LocalUser
import org.bukkit.entity.Arrow
import org.bukkit.entity.Player
import org.bukkit.entity.Projectile
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.ProjectileLaunchEvent
import org.bukkit.projectiles.ProjectileSource

object ProjectileLaunchListener : Listener {

  val projectiles: MutableSet<Int> = mutableSetOf()

  @EventHandler
  fun handleProjectileLaunch(
    event: ProjectileLaunchEvent
  ) {
    //
    if (event.isCancelled) {
      return
    }

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
    if (false == arena.map?.isLoaded || !game.isInArena(user)) {
      return
    }

    //
    val ability: Ability = game.findTeammateByHostage(user)
      ?.currentProfession
      ?.ability
      ?: return

    //
    if (!ability.isActive) {
      return
    }

    projectiles.add(projectile.entityId)
    ability.renewDelayed(shooter)
  }
}