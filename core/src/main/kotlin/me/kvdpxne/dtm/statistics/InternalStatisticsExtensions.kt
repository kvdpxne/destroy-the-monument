package me.kvdpxne.dtm.statistics

internal fun BasicStatistics.add(
  current: Int,
  next: Int
): Int {
  if (0 == next) {
    return current
  }

  val newValue = current + next
  if (0 > newValue) {
    return 0
  }

  this.markAsModified()
  return newValue
}

internal fun BasicStatistics.subtract(
  current: Int,
  next: Int
): Int {
  if (0 == next) {
    return current
  }

  val newValue = current - next
  if (0 > newValue) {
    return 0
  }

  this.markAsModified()
  return newValue
}