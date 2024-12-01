package me.kvdpxne.dtm.user

import me.kvdpxne.dtm.game.LocalGame
import me.kvdpxne.dtm.profession.Profession
import me.kvdpxne.dtm.shared.Communicative
import me.kvdpxne.dtm.team.LocalTeam
import me.kvdpxne.dtm.team.Teammate
import me.kvdpxne.dtm.translation.chains.MessageFormatterChains
import me.kvdpxne.dtm.translation.message.EnumMessageKey

/**
 * Extends the [User] interface, representing a user with additional localized
 * properties and methods for in-game interactions. The [LocalUser] interface
 * includes caching, performance, and communication capabilities, as well as
 * associations with a game, team, and teammate.
 *
 * @since 0.1.0
 */
interface LocalUser : User, Communicative {

  /**
   * Provides access to a cache specific to this user, storing various
   * temporary or frequently accessed data for optimized performance.
   *
   * @since 0.1.0
   */
  val cache: LocalUserCache

  /**
   * Represents an object responsible for performing actions on behalf of this
   * user.
   *
   * The [LocalUserPerformer] may handle complex actions or behavior that the
   * user can initiate.
   *
   * @since 0.1.0
   */
  val performer: LocalUserPerformer

  /**
   * The current game instance the user is associated with, if any. This
   * reference may be null if the user is not currently engaged in an active
   * game session.
   *
   * @since 0.1.0
   */
  val game: LocalGame?

  /**
   * The team this user is currently a part of within the game, or null if the
   * user has no team affiliation. This property allows the user to access
   * team-specific data and interactions.
   *
   * @since 0.1.0
   */
  val team: LocalTeam?

  /**
   * The teammate information of this user within the context of a team.
   * This may include roles, responsibilities, or other team-related attributes
   * specific to this user. Returns null if the user is not part of any team.
   *
   * @since 0.1.0
   */
  val teammate: Teammate?

  /**
   * Updates the user's current profession to the specified [Profession].
   * This may affect the user's abilities or role in the game depending on the
   * profession chosen.
   *
   * @param profession The new profession to assign to the user.
   * @since 0.1.0
   */
  fun updateCurrentProfession(
    profession: Profession
  )

  fun prepareMessage(key: EnumMessageKey): MessageFormatterChains {
    return this.performer.prepareMessage(key)
  }
}