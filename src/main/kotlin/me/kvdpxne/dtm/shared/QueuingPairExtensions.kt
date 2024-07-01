package me.kvdpxne.dtm.shared

fun <T> queuingPair(current: T, next: T? = null): QueuingPair<T> {
  return QueuingPair(current, next)
}

fun <T> T.toQueuingPair(): QueuingPair<T> {
  return QueuingPair(this)
}