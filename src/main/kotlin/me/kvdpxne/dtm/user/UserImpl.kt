package me.kvdpxne.dtm.user

import java.util.UUID
import me.kvdpxne.dtm.data.state.BaseIdentifiableMutableState
import me.kvdpxne.dtm.profession.Profession
import me.kvdpxne.dtm.shared.StylishToStringBuilder
import me.kvdpxne.dtm.wallet.Wallet

/**
 * Implementation of the [User] interface, representing a user with a unique
 * identifier, wallet, profession, statistics, and display information.
 *
 * This class serves as a mutable state-tracking entity with conversion
 * capabilities to a more specific [LocalUser] type.
 *
 * @param name The unique name identifier for the user.
 * @param displayName The display name of the user, which may be shown in the UI.
 * @param statistics The user's performance and engagement metrics.
 * @param wallet The user's wallet, which manages their coin balance and
 *               transaction multipliers.
 * @param currentProfession The user's current profession, which may impact
 *                          their in-game role.
 * @param identifier A unique [UUID] identifier for the user.
 *
 * @since 0.1.0
 */
open class UserImpl internal constructor(
  // @formatter:off
  override val name             : String,
  override val displayName      : String,
  override val statistics       : UserStatistics,
  override val wallet           : Wallet,
  override var currentProfession: Profession,
               identifier       : UUID
  // @formatter:on
) : BaseIdentifiableMutableState<UUID>(identifier), User {

  override fun asLocalUser(): LocalUser {
    return LocalUserImpl(
      this.name,
      this.displayName,
      this.statistics,
      this.wallet,
      this.currentProfession,
      this.identifier
    )
  }

  /**
   * Generates a string representation of the user, including the identifier,
   * name, display name, statistics, wallet, and current profession.
   *
   * Uses [StylishToStringBuilder] to format the output.
   *
   * @return A formatted string representation of the user.
   *
   * @since 0.1.0
   */
  override fun toString(): String {
    return StylishToStringBuilder()
      .begin("User")
      .add("identifier", this.identifier)
      .add("name", this.name)
      .add("displayName", this.displayName)
      .add("statistics", this.statistics)
      .add("wallet", this.wallet)
      .add("currentProfession", this.currentProfession)
      .build()
  }
}