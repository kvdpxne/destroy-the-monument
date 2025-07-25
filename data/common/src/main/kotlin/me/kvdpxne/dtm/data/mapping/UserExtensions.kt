package me.kvdpxne.dtm.data.mapping

import me.kvdpxne.dtm.data.raw.RawUser
import me.kvdpxne.dtm.data.shared.mapNotNullTo
import me.kvdpxne.dtm.user.User

/**
 * @since 0.1.0
 */
fun User.toRawUser(): RawUser {
  return RawUser(
    this.identifier,
    this.statistics.toRawUserStatistics(),
    this.wallet.toRawUserWallet(),
    this.name,
    this.profession.name,
    this.localeSource.localization
  )
}

/**
 * @param initialCapacity
 * @param distinct
 *
 * @since 0.1.0
 */
fun Iterable<User?>.toRawUsers(
  initialCapacity: Int = 12,
  distinct: Boolean = true
): List<RawUser> {
  return mapNotNullTo(
    this,
    initialCapacity,
    distinct,
    User::toRawUser
  )
}