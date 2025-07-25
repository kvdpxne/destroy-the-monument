package me.kvdpxne.dtm.shared

/**
 * @since 0.1.0
 */
val REGEX_SINGLE_LINE: Regex by lazy {
  "(\n*)\n".toRegex()
}

/**
 * @since 0.1.0
 */
fun String.toSingleLines(): String {
  this.uppercase()
  return this.trimIndent().replace(
    REGEX_SINGLE_LINE,
    "$1"
  )
}