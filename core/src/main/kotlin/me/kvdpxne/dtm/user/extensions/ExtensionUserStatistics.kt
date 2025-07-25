package me.kvdpxne.dtm.user.extensions

import me.kvdpxne.dtm.data.raw.RawUserStatistics
import me.kvdpxne.dtm.user.statistics.BasicUserStatistics
import me.kvdpxne.dtm.user.statistics.UserStatistics

/**
 * @since 0.1.0
 */
fun RawUserStatistics.toUserStatistics(): UserStatistics {
  return BasicUserStatistics(
    // @formatter:off
    identifier                = this.identifier,
    initialKills              = this.kills,
    initialAssists            = this.assists,
    initialDeaths             = this.deaths,
    initialDestroyedMonuments = this.destroyedMonuments,
    initialPlayedGames        = this.playedGames,
    initialGamesWon           = this.gamesWon,
    initialGamesLost          = this.gamesLost,
    // Początkowy stan modyfikacji obiektu w tym przypadku powinien być zawsze
    // ustawiony na fałsz, ponieważ dane pobrane z zewnętrznego źródła nie
    // zostały jeszcze zmodyfikowane (uaktualnienie nie jest potrzebne).
    initialModified           = false
    // @formatter:on
  )
}