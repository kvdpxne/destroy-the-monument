package me.kvdpxne.dtm.shared.language

/**
 * @since 0.1.0
 */
fun String.toSingleLines(): String {
  return this.trimIndent().replace(
    LazyStringHolder.SINGLE_LINE_REGEX,
    "$1"
  )
}