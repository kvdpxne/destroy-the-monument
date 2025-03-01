package me.kvdpxne.dtm.data.raw.extensions

import me.kvdpxne.dtm.data.raw.RawUser
import me.kvdpxne.dtm.user.User
import me.kvdpxne.dtm.user.UserInvalidNameException

/**
 *
 */
fun User.toRawUser(): RawUser {
  val trimmedName: String = this.name.trim()

  if (trimmedName.length !in 3..16) {
    throw UserInvalidNameException(
      "The user name must be longer than 2 characters and shorter than 17 characters.",
    )
  }

  if (trimmedName.matches("^[a-zA-Z0-9_]+".toRegex())) {
    throw UserInvalidNameException(
      "The username must consist only of lowercase and uppercase ASCII letters, numbers, and an underscore character."
    )
  }

  val lowerName: String = trimmedName.lowercase()
  val lowerProfessionName: String = this.currentProfession.name.lowercase()
  val lowerLocalization: String = this.localeSource.localization.lowercase()

  return RawUser(
    this.identifier,
    this.statistics.toRawUserStatistics(),
    this.wallet.toRawUserWallet(),
    lowerName,
    this.displayName,
    lowerProfessionName,
    lowerLocalization,
  )
}