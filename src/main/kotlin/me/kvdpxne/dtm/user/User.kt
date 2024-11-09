package me.kvdpxne.dtm.user

import java.util.UUID
import me.kvdpxne.dtm.data.state.MutableState
import me.kvdpxne.dtm.profession.Profession
import me.kvdpxne.dtm.shared.Identifiable
import me.kvdpxne.dtm.shared.Nameable
import me.kvdpxne.dtm.shared.PlayerUuid
import me.kvdpxne.dtm.wallet.Wallet

/**
 * Represents a user in the system, identified by a unique [UUID] and possessing
 * attributes such as a wallet, statistics, and a current profession.
 *
 * This interface allows retrieval of detailed user information and facilitates
 * state tracking through [MutableState].
 *
 * @since 0.1.0
 */
interface User : Identifiable<PlayerUuid>, Nameable, MutableState {

  /**
   * Holds the user's performance and engagement metrics, represented by
   * [UserStatistics].
   *
   * These statistics may track achievements, scores, or other relevant data.
   *
   * @since 0.1.0
   */
  val statistics: UserStatistics

  /**
   * Represents the user's wallet, managing the user's coin balance and
   * transaction multipliers.
   *
   * The wallet allows for adding and subtracting coin values and tracks any
   * changes to its state.
   *
   * @since 0.1.0
   */
  val wallet: Wallet

  /**
   * @since 0.1.0
   */
  val currentProfession: Profession

  /**
   * Converts the current user instance into a [LocalUser], representing a more
   * specific type of user with additional, localized functionalities or
   * properties.
   *
   * @return An instance of [LocalUser], representing the user in a localized
   *         context.
   * @since 0.1.0
   */
  fun asLocalUser(): LocalUser
}