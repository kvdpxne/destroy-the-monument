package me.kvdpxne.dtm.listeners.player

import me.kvdpxne.dtm.user.LocalUserManager
import me.kvdpxne.dtm.user.User
import me.kvdpxne.dtm.user.UserBuilder
import me.kvdpxne.dtm.user.UserService
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerLoginEvent

/**
 * Listener for player login events in the game.
 *
 * The [PlayerLoginListener] handles the logic required to verify and set up a
 * user's data when they log into the game. If the player is permitted to join
 * (i.e., their login attempt is successful), the listener ensures the player
 * has a corresponding `User` object in the system. If the user does not exist
 * in the [UserService], they are created and added to the [LocalUserManager].
 *
 * @since 0.1.0
 */
object PlayerLoginListener : Listener {

  /**
   * Handles the player login event.
   *
   * This method is triggered each time a player attempts to log in. It checks
   * whether the login was allowed. If allowed, it then verifies if the player
   * already has a corresponding [User] object in the system. If not, it
   * creates a new user with default settings using the [UserBuilder]. The user
   * is then registered with the [UserService] and added to the
   * [LocalUserManager] to enable in-game tracking and management.
   *
   * @param event The [PlayerLoginEvent] instance containing the player
   *              login details.
   * @see PlayerLoginEvent
   * @since 0.1.0
   */
  @EventHandler(
    priority = EventPriority.MONITOR
  )
  fun handlePlayerLogin(
    event: PlayerLoginEvent
  ) {
    // Proceed only if the login attempt was successful.
    if (PlayerLoginEvent.Result.ALLOWED != event.result) {
      return
    }

    val player: Player = event.player

    // Attempt to find the user by their unique identifier.
    var user: User? = UserService.findUserByIdentifier(player.uniqueId)

    // If the user does not exist, create a new user for this player.
    if (null == user) {
      user = UserBuilder.create(player).build()
      UserService.createUser(user)
    }

    // Add the user to the local user manager.
    LocalUserManager.addUser(user)
  }
}