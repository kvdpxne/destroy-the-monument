package me.kvdpxne.dtm.listener

import me.kvdpxne.dtm.data.DaoUser
import me.kvdpxne.dtm.shared.hardClean
import me.kvdpxne.dtm.shared.toBuilder
import me.kvdpxne.dtm.user.UserManager
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

val JOIN_GAME_ITEM = Material.NETHER_STAR.toBuilder()
  .name("&e&lDołącz do gry")
  .build()

val SELECT_TEAM_ITEM = Material.NETHER_STAR.toBuilder()
  .name("&e&lWybierz drużyne")
  .build()

val SELECT_PROFESSION_ITEM = Material.IRON_AXE.toBuilder()
  .name("&e&lWybierz profesje")
  .build()

val ITEM_GAME_LEAVE = Material.WEB.toBuilder()
  .name("&c&lOpuść gre")
  .build()

object PlayerJoinListener : Listener {

  @EventHandler
  fun handlePlayerJoin(event: PlayerJoinEvent) {
    val player = event.player

    val identifier = player.uniqueId
    val name = player.name

    var user = DaoUser.findByIdentifier(identifier)
    if (null == user) {
      user = UserManager.createUser(identifier, name)
      DaoUser.insert(user)
    } else {
      UserManager.addUser(user)
    }

    player.teleport(player.world.spawnLocation)
    player.hardClean()

    player.inventory.setItem(0, JOIN_GAME_ITEM)
  }
}