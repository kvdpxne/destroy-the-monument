package me.kvdpxne.dtm.shared

import io.github.oshai.kotlinlogging.KLogger

fun KLogger.debug(condition: Boolean, message: () -> String) {
  if (condition) {
    debug {
      message()
    }
  }
}