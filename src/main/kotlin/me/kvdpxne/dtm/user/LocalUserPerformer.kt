package me.kvdpxne.dtm.user

import java.util.Locale
import me.kvdpxne.dtm.command.Performer
import me.kvdpxne.dtm.game.LocalGame
import me.kvdpxne.dtm.shared.Communicative
import me.kvdpxne.dtm.shared.Identifiable
import me.kvdpxne.dtm.shared.PlayerUuid
import me.kvdpxne.dtm.team.LocalTeam
import me.kvdpxne.dtm.team.Teammate
import org.bukkit.entity.Player

/**
 * Defines a contract for objects that act on behalf of a [LocalUser] within
 * the game environment.
 *
 * This interface provides the necessary functions and properties for in-game
 * actions, communication, and state management of a user (represented by a
 * [Player]) within a Minecraft server context.
 *
 * @since 0.1.0
 */
interface LocalUserPerformer : Identifiable<PlayerUuid>, Communicative, Performer {

  /**
   * The [LocalUser] instance associated with this performer, providing access
   * to user-specific data and operations.
   *
   * @since 0.1.0
   */
  val user: LocalUser

  /**
   * @since 0.1.0
   */
  val game: LocalGame?
    get() = this.user.game

  /**
   * @since 0.1.0
   */
  val team: LocalTeam?
    get() = this.user.team

  /**
   * @since 0.1.0
   */
  val teammate: Teammate?
    get() = this.user.teammate

  /**
   * The Bukkit [Player] object representing the user in-game. This may be null
   * if the user is not currently online or the player object hasn't been
   * initialized.
   *
   * @since 0.1.0
   */
  val player: Player?

  /**
   * The locale of the user, which determines the language and region-specific
   * settings used for communication with the user. This can be modified to
   * update the user’s preferred language settings.
   *
   * @since 0.1.0
   */
  val locale: Locale

  /**
   * Indicates whether the user is currently online. Returns `true` if the user
   * is connected to the server; otherwise, `false`.
   *
   * @since 0.1.0
   */
  val isOnline: Boolean

  /**
   * Checks if the user has operator status on the server, allowing them
   * to access to higher-level commands and permissions.
   *
   * @since 0.1.0
   */
  val isOperator: Boolean
}