package me.kvdpxne.dtm.shared

inline fun <T> Array<out T>.indexOfSecond(predicate: (T) -> Boolean): Int {
  var isSecond = false
  for (index in this.indices) {
    if (!predicate(this[index])) {
      continue
    }
    if (isSecond) {
      return index
    }
    isSecond = true
  }
  return -1
}