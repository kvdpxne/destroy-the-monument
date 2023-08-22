package me.kvdpxne.dtm.listener

import me.kvdpxne.dtm.game.GameManager
import me.kvdpxne.dtm.user.UserManager
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent

object PlayerDeathListener : Listener {

  @EventHandler
  fun handlePlayerDeath(event: PlayerDeathEvent) {
    val user = UserManager.getUserByIdentifierOrNull(event.entity.uniqueId) ?: return
    val game = GameManager.findByUser(user) ?: return

    if (game.isInArenaMap(user)) {
      event.drops.clear()
      event.droppedExp = 0

      val victim = event.entity
      val murder = victim.killer

      if (null == murder) {
        event.deathMessage = "${victim.displayName} zginął"
        return
      }

      event.deathMessage = "${victim.displayName} został zabity przez ${murder.displayName}"
    }
  }
}