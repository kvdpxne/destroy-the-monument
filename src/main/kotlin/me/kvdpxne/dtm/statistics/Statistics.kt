package me.kvdpxne.dtm.statistics

class Statistics(
  // @formatter:off
  var kills             : Int = 0,
  var assists           : Int = 0,
  var deaths            : Int = 0,
  var playedGames       : Int = 0,
  var gamesWon          : Int = 0,
  var gamesLost         : Int = 0,
  var destroyedMonuments: Int = 0
  // @formatter:on
) {

  companion object {

    fun empty(): Statistics {
      return Statistics(0, 0, 0)
    }
  }
}