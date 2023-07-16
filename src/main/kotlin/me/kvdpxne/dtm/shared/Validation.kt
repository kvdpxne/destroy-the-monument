package me.kvdpxne.dtm.shared

fun isTrue(condition: Boolean, message: String) {
  if (condition) {
    throw ValidationFailedException(message)
  }
}

fun isFalse(condition: Boolean, message: String) {
  isTrue(!condition, message)
}
