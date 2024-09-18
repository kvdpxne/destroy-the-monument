package me.kvdpxne.dtm.user

import me.kvdpxne.dtm.profession.Profession
import me.kvdpxne.dtm.wallet.Wallet

open class UserImpl internal constructor(
  // @formatter:off
  override val name             : String,
  override val displayName      : String,
  override val statistics       : UserStatistics,
  override val wallet           : Wallet,
  override var currentProfession: Profession,
  override val identifier       : String,
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
}