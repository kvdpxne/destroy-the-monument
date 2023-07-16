package me.kvdpxne.dtm.statistics

class Statistics(
  var kills: Int,
  var assists: Int,
  var deaths: Int
) {

  companion object {

    fun empty(): Statistics {
      return Statistics(0, 0, 0)
    }
  }
}