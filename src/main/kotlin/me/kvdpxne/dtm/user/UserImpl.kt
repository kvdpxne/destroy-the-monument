package me.kvdpxne.dtm.user

import java.util.UUID
import me.kvdpxne.dtm.profession.Profession
import me.kvdpxne.dtm.shared.StylishToStringBuilder
import me.kvdpxne.dtm.wallet.Wallet

open class UserImpl internal constructor(
  // @formatter:off
  override val name             : String,
  override val displayName      : String,
  override val statistics       : UserStatistics,
  override val wallet           : Wallet,
  override var currentProfession: Profession,
  override val identifier       : UUID
  // @formatter:on
) : User {

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