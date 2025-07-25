package me.kvdpxne.dtm.user.extensions

import me.kvdpxne.boujee.locale.BasicLocaleSource
import me.kvdpxne.dtm.data.raw.RawUser
import me.kvdpxne.dtm.user.BasicUser
import me.kvdpxne.dtm.user.User

fun RawUser.toUser(): User {
  return BasicUser(
    // @formatter:off
    identifier           = this.identifier,
    initialStatistics    = this.statistics.toUserStatistics(),
    initialWallet        = this.wallet.toUserWallet(),
    name                 = this.name,
    displayName          = this.displayName,
    professionName       = this.profession,
    localeSource         = BasicLocaleSource(this.locale),
    // Początkowy stan modyfikacji obiektu w tym przypadku powinien być zawsze
    // ustawiony na fałsz, ponieważ dane pobrane z zewnętrznego źródła nie
    // zostały jeszcze zmodyfikowane (uaktualnienie nie jest potrzebne).
    initialModifiedState = false
    // @formatter:on
  )
}