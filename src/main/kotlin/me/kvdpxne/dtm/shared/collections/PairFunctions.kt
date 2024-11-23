package me.kvdpxne.dtm.shared.collections

/**
 * @since 0.1.0
 */
fun <T> Iterable<T>.toPair(): Pair<T, T> {
  if (2 != this.count()) {
    error("")
  }

  val iterator: Iterator<T> = this.iterator()

  val first: T = iterator.next()
  val second: T = iterator.next()

  return Pair(first, second)
}